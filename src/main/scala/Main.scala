package com.wlangiewicz.btc.scattercoin

import java.io.File
import java.net.InetAddress
import com.google.common.util.concurrent.MoreExecutors
import org.bitcoinj.net.discovery.DnsDiscovery

import scala.collection.JavaConverters._

import org.bitcoinj.core._
import org.bitcoinj.params.{RegTestParams, MainNetParams, TestNet3Params}
import org.bitcoinj.store.{SPVBlockStore, MemoryBlockStore}

import scala.io.Source

object Main extends App {
  override def main(args: Array[String]): Unit = {
    def processInputs(): Scatter = {
      val params: NetworkParameters = args(0) match {
        case "main" => MainNetParams.get
        case "regtest" => RegTestParams.get
        case "testnet" => TestNet3Params.get
      }
      val file: File = new File(args(1))

      Console.println(f"reading file ${file.getAbsolutePath}/${file.getName}")
      new Scatter(params, file)
    }

    if (args.length != 2) {
      Console.println("USAGE: sbt \"run-main com.wlangiewicz.btc.scattercoin.Main testnet|main /path/to/file/with/addresses.txt\"")
    }
    else {
      val s: Scatter = processInputs
    }
  }
}

class Scatter(params: NetworkParameters, file: File) {
  Console.println("Creating Scatter")
  private val source = Source.fromFile(file)
  val privateKeys: List[String] = source.getLines().toList map { //private keys in WIF format
    _.split(",")(0)
  }

  val privateECKeys: List[ECKey]= privateKeys map { //
    key: String => addressToKey(params, key)
  }

  val wallet = new Wallet(params)
  val blockStore: MemoryBlockStore = new MemoryBlockStore(params)
  val chain: BlockChain = new BlockChain(params, wallet, blockStore)
  val peerGroup: PeerGroup = new PeerGroup(params, chain)

  importAllKeys()
  peerGroup.addWallet(wallet)

  printAllPublicAddresses()

  peerGroup.connectToLocalHost()
  Console.println("Connected to localhost")
  //peerGroup.addPeerDiscovery(new DnsDiscovery(params))
  peerGroup.startAsync
  peerGroup.downloadBlockChain

  val balance = wallet.getBalance
  Console.println("total balance is: " + balance.toFriendlyString)
//  val targetBalance = balance.divide(wallet.getImportedKeys.size()).subtract(Transaction.REFERENCE_DEFAULT_MIN_TX_FEE)
//  Console.println("target balance is: " + targetBalance.toFriendlyString)
  val destination: Address = new Address(params, "mrCLUhatyJF4JZby59cEoRjomHEZjxQXnr")

  Console.println("Claiming " + wallet.getBalance.toFriendlyString)
  wallet.sendCoins(peerGroup, destination, balance.subtract(Coin.COIN))

  peerGroup.stopAsync
  Thread.sleep(5000)


  def importAllKeys(): Unit ={
    Console.println("Importing keys")
    wallet.importKeys(privateECKeys.asJava)
    Console.println("Keys Added")
  }

  def printTotalCoins(): Unit = {
    Console.println(wallet.getBalance.toFriendlyString)
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
    val toPrint = privateKeys mkString ("\n")
    Console.println("read following private keys:\n" + toPrint)
  }
}