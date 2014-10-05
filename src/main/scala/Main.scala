package com.wlangiewicz.btc.scattercoin

import java.io.File
import java.net.InetAddress

import org.bitcoinj.core._
import org.bitcoinj.net.discovery.DnsDiscovery
import org.bitcoinj.params.{MainNetParams, TestNet3Params}
import org.bitcoinj.store.{SPVBlockStore, MemoryBlockStore}

import scala.io.Source

object Main extends App {
  override def main(args: Array[String]): Unit = {
    def processInputs(): Scatter = {
      val params: NetworkParameters = args(0) match {
        case "main" => MainNetParams.get
        case _ => TestNet3Params.get
      }


      val file: File = new File(args(1))

      Console.println(f"reading file ${file.getAbsolutePath}/${file.getName}")
      new Scatter(params, file)
    }

    if (args.length != 2) {
      Console.println("USAGE: sbt \"run-main com.wlangiewicz.btc.scattercoin.Main testnet|main /path/to/file/with/addresses.txt\"")
    }
    else {
      val s: Scatter = processInputs()
      //s.displayPrivateKeys()
      //s.printAllPublicAddresses
    }
  }


}

class Scatter(params: NetworkParameters, file: File) {
  Console.println("Creating Scatter")
  private val source = Source.fromFile(file)
  val privateKeys = source.getLines().toList map {
    _.split(",")(0)
  }

  val privateECKeys = privateKeys map {
    key: String => addressToKey(params, key)
  }

  val wallet = new Wallet(params)

  val blockStore: MemoryBlockStore = new MemoryBlockStore(params)

  val chain: BlockChain = new BlockChain(params, wallet, blockStore)
  val peerGroup: PeerGroup = new PeerGroup(params, chain)
  peerGroup.addWallet(wallet)

  importAllKeys

  peerGroup.addAddress(new PeerAddress(InetAddress.getLocalHost))
  Console.println("Connected to localhost")
  //peerGroup.addPeerDiscovery(new DnsDiscovery(params))
  peerGroup.startAsync
  peerGroup.downloadBlockChain
  peerGroup.stopAsync
  Console.println("TOTAL BALANCE: " + wallet.getBalance.toFriendlyString)

  Console.println("Scatter created")

  def importAllKeys(): Unit ={
    Console.println("Importing keys")
    privateECKeys foreach {
      key => wallet.importKey(key)
    }
    Console.println("Keys Added")
  }

  def totalCoins(): Unit = {
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

  def displayPrivateKeys(): Unit = {
    val toPrint = privateKeys mkString ("\n")
    Console.println("read following private keys:\n" + toPrint)
  }
}