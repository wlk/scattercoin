package com.wlangiewicz.btc.scattercoin

import java.io.File
import org.bitcoinj.net.discovery.DnsDiscovery

import scala.collection.JavaConverters._

import org.bitcoinj.core._
import org.bitcoinj.params.{RegTestParams, MainNetParams, TestNet3Params}
import org.bitcoinj.store.MemoryBlockStore

import scala.io.Source

object Main extends App {
  override def main(args: Array[String]): Unit = {
    def getScatterCoinMain(): ScatterCoinMain = {
      val params: NetworkParameters = args(0) match {
        case "main" => MainNetParams.get
        case "regtest" => RegTestParams.get
        case "testnet" => TestNet3Params.get
      }
      val file: File = new File(args(1))

      Console.println(f"reading file ${file.getAbsolutePath}/${file.getName}")
      new ScatterCoinMain(params, file)
    }

    if (args.length != 2) {
      Console.println("USAGE: sbt \"run-main com.wlangiewicz.btc.scattercoin.Main testnet|main|regtest /path/to/file/with/addresses.txt\"")
    }
    else {
      val s: ScatterCoinMain = getScatterCoinMain()
    }
  }
}

class ScatterCoinMain(params: NetworkParameters, file: File) {
  Console.println("Creating Scatter")
  private val source = Source.fromFile(file)

  val privateKeys: List[String] = source.getLines().toList map { //private keys in WIF format
    _.split(",")(0)
  }
  val privateECKeys: List[ECKey]= privateKeys map {
    key: String => addressToKey(params, key)
  }

  val wallet = setupWallet()
  importAllKeys()

  printAllPublicAddresses()
  val peerGroup = setupPeerGroup(wallet)

  Console.println("target balance is: " + targetBalance.toFriendlyString + "\nTotal balance is: " + wallet.getBalance.toFriendlyString)
  if(targetBalance.isGreaterThan(Coin.SATOSHI)) {
    processTransactions()
  }
  else{
    Console.println("Not enough founds")
  }
  peerGroup.stopAsync
  Thread.sleep(5000)

  def processTransactions(): Unit ={
    Console.println("adding outputs")

    //here we are relying on the bitcoinj to optimize how transaction inputs and outputs are used
    val tx: Transaction = new Transaction(params)
    privateECKeys foreach {
      key => tx.addOutput(targetBalance, key.toAddress(params))
    }
    Console.println("outputs added")

    val request: Wallet.SendRequest = Wallet.SendRequest.forTx(tx)

    wallet.completeTx(request)
    wallet.commitTx(request.tx)

    Console.println("broadcasting transaction")
    peerGroup.broadcastTransaction(request.tx).get

    Console.println(tx)
  }

  def setupWallet(): Wallet ={
    val wallet = new Wallet(params)
    wallet
  }

  def setupPeerGroup(wallet: Wallet): PeerGroup = {
    val blockStore: MemoryBlockStore = new MemoryBlockStore(params)
    val chain: BlockChain = new BlockChain(params, wallet, blockStore)
    val peerGroup: PeerGroup = new PeerGroup(params, chain)
    peerGroup.addWallet(wallet)
    if(params.equals(RegTestParams.get)){
      peerGroup.connectToLocalHost()
    }
    else{
      peerGroup.addPeerDiscovery(new DnsDiscovery(params))
    }
    peerGroup.startAsync
    Console.println("Starting blockchain download")
    peerGroup.downloadBlockChain()
    Console.println("Blockchain download done")

    peerGroup
  }

  lazy val targetBalance: Coin ={
    val balance = wallet.getBalance
    balance.divide(wallet.getImportedKeys.size()).subtract(Transaction.REFERENCE_DEFAULT_MIN_TX_FEE)
  }

  def importAllKeys(): Unit ={
    Console.println("Importing keys")
    wallet.importKeys(privateECKeys.asJava)
    Console.println("Keys Added")
  }

  def printAllPublicAddresses(): Unit ={
    privateECKeys foreach printPublicAddress
  }

  def printPublicAddress(key: ECKey): Unit ={
    Console.println("Address from private key " + key + " is: " + key.toAddress(params) )
  }

  def addressToKey(params: NetworkParameters, sourceAddress: String): ECKey = {
    sourceAddress match {
      case _ if sourceAddress.length == 51 || sourceAddress.length == 52 => new DumpedPrivateKey(params, sourceAddress).getKey
      case _ => ECKey.fromPrivate(Base58.decodeToBigInteger(sourceAddress))
    }
  }

  def printPrivateKeys(): Unit = {
    val toPrint = privateKeys mkString "\n"
    Console.println("read following private keys:\n" + toPrint)
  }
}