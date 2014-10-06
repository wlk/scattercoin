This is simple bitcoin tool that can be used to scatter BTC for a list of addresses

##Example

I have 3 bitcoin addresses with following amounts:  

* Address X has 3 BTC  
* Address Y has 1 BTC  
* Address Z has 0 BTC  

After running this tool I'll have following amounts on each address:  

* Address X will have ~1.33BTC  
* Address Y will have ~1.33BTC  
* Address Z will have ~1.34BTC  

It works both on main and test bitcoin network.

Tool will pay the default fee (not included in this calculation)

##Use cases
I use it on the testnet like this:
1. Generate multiple addresses (including private keys)
2. Request [faucet](http://tpfaucet.appspot.com) to send 50BTC to any of the addresses
3. Run the tool
4. Each address will have more or less even number of coins
5. Use addresses for testing

##Running

###Testnet
```
sbt "run-main com.wlangiewicz.btc.scattercoin.Main testnet /path/to/file/with/addresses.txt"
```

###Main network
```
sbt "run-main com.wlangiewicz.btc.scattercoin.Main main /path/to/file/with/addresses.txt"
```

###Regtest network
```
sbt "run-main com.wlangiewicz.btc.scattercoin.Main regtest /path/to/file/with/addresses.txt"
```

##This is how it works:
1. Read Bitcoin private keys from input file
2. Calculate te sum of BTC stored on those addresses
3. Transfer coins between addresses so each of them will have as even amount of coins as possible

