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

##Output after running on testnet:  
```
sbt "run-main com.wlangiewicz.btc.scattercoin.Main testnet /home/w/private-keys/bitcoinpaperwallet.com"
[info] Loading project definition from /home/w/bitcoin-scattercoins/project
[info] Set current project to bitcoin-scattercoins (in build file:/home/w/bitcoin-scattercoins/)
[info] Updating {file:/home/w/bitcoin-scattercoins/}bitcoin-scattercoins...
[info] Resolving jline#jline;2.12 ...
[info] Done updating.
[info] Compiling 1 Scala source to /home/w/bitcoin-scattercoins/target/scala-2.11/classes...
[warn] there was one deprecation warning; re-run with -deprecation for details
[warn] one warning found
[info] Running com.wlangiewicz.btc.scattercoin.Main testnet /home/w/private-keys/bitcoinpaperwallet.com
reading file /home/w/private-keys/bitcoinpaperwallet.com/bitcoinpaperwallet.com
Creating Scatter
Importing keys
Keys Added
Address from private key ECKey{pub=0371b6ccd87a22d6b3e9e6b2802ed8fb0e25d3837edb1bfc047e8c460c7c555003, isEncrypted=false} is: mi1ZfmWbqGiPwjYLmnrrVgAfLkp8jhvH3H
Address from private key ECKey{pub=0415da5e21e90b6f106dc477250a758a80de4300bdb7bca8ac24e157bb7f2f9374bc486437a528b847b3d741f6549369348b00216d6d8d6118fd72c427d32cfe19, isEncrypted=false} is: msoGGRkYLWxvEwFYUJtpUiNjMALyM5xyLf
Address from private key ECKey{pub=04342f88b24dddadfc31a42901263e3283278b3164b9f65ab5b2343a6e1e1f178cec9510e1798b4e61b9777ce398c1ad99b247b7b12febb811d3fb1e6c6cbe8844, isEncrypted=false} is: msZDpyYGmdFrEuLwYdFWwFYeUzgYCpevUr
Address from private key ECKey{pub=04cb4cde356e27d04d78643bc3360e53a0154340958924aa85fe55d3590f88cc9e4444978834775989b00789cf7bf5fdbc709f8cc4338183599fd79da2f42cdef3, isEncrypted=false} is: mkZ1SP7Y89MUqAjWCey5pae6gTMWm53SCW
Address from private key ECKey{pub=04fbfb50279d5ec8dbb688d41fec3a73b82ce2bd48a8a80862d63f29c2d811e807a3ae7cf87c7ccbddac9b91ded46b960b85885910e6d19cb38d982ed5040a3974, isEncrypted=false} is: mmFisd9e15A2xwaL8dJDauverM62Fc4Fno
Address from private key ECKey{pub=044fb58cf931c2a38f8c1c864db7ba3e18a9450c69d2f53519f2086358179c62830b16e6d31f7d1da45015b60f9a991dd0b290f366a27df9aa98e810467389a25c, isEncrypted=false} is: mxyNm1SNZyM9Fq1b3vHSMPCCgVLPsbLz3S
Address from private key ECKey{pub=041f19ffb5944bafdbc4de9331dce15dc63c3a16abf4e9ccfd0454a3716f676500a77bb00cfec0d797d28054b867c498856a13512cfa98a6e2fb331ebcd746255f, isEncrypted=false} is: mgbZUZ9zkpyZgQz6F9G55ggC2MXeWZXrrz
Address from private key ECKey{pub=04cc9fdee6dd8d53e73d51876160cc537f5b7c829340350e3072a1be12fc04a2f61e3bdade101f0350c828df75ee252299fcb8c4e60074831e7e04dc3bf33b392e, isEncrypted=false} is: mw8i3PXCsETiSickmahDTXxdXk55YqtpQd
Connected to localhost
total balance is: 103.00 BTC
target balance is: 12.8749 BTC
adding outputs
outputs added
broadcasting transaction
  0b14cc8851221b8fba8258faf1e76be7b7bab0bbc2fd2382edaf86bfc7cfc230: Pending/unconfirmed.
     in   PUSHDATA(72)[3045022100d1ba5ef9028a062fe24435359ca3ddba7c6adbbc24e38aa8fbd5582d2144855202200b33db11ca569a7176422f9274975dbb4c0e19a351a45fa9e05283efb32ed85b01] PUSHDATA(65)[04cb4cde356e27d04d78643bc3360e53a0154340958924aa85fe55d3590f88cc9e4444978834775989b00789cf7bf5fdbc709f8cc4338183599fd79da2f42cdef3] 1.00 BTC
          outpoint:f5f0500f5d5f2e2e82325434929ff0d069f366034df8d1a11a3ba51faef17a65:1 hash160:373c15c109edd90d39dbc1f9cb1578cd7d988266
     in   PUSHDATA(72)[3045022100e0eee766149056b360c60e257b001fbaf732d6ea37f660db896bf54b29640af80220085fa37b2b6fcd611fd0a2c2183a0608e59ff06fc7babe3c87bda3395902d4e301] PUSHDATA(65)[044fb58cf931c2a38f8c1c864db7ba3e18a9450c69d2f53519f2086358179c62830b16e6d31f7d1da45015b60f9a991dd0b290f366a27df9aa98e810467389a25c] 1.00 BTC
          outpoint:c0061e7603fcc66291dd3d2c6b6963c6cba36dca47e53f966b56c9f310f15257:0 hash160:bf79601e126c46d91e3f7ae31aec345785e3f33b
     in   PUSHDATA(72)[3045022100845988c36f58c761d475887a7d6900dfbb1f6e7c9d70c4d828e9123c7f6e50f90220794d9de4e9ae36f57eae16b647470c5c0ab4a9e10ed63dd579f0774aeda9408d01] PUSHDATA(65)[04fbfb50279d5ec8dbb688d41fec3a73b82ce2bd48a8a80862d63f29c2d811e807a3ae7cf87c7ccbddac9b91ded46b960b85885910e6d19cb38d982ed5040a3974] 1.00 BTC
          outpoint:8db329e9e0ede22e6a49c78caa80062841d96132de08dac303ab84cb19587485:0 hash160:3eef4fd341c73f125b8165f894b4e6eeb6a30de3
     in   PUSHDATA(72)[3045022100d0f42b3f36314a186455a4193fe0ef6ea1de0f2be5e50f999943833a6bd291100220623673eebfaae20f18b21078bd7f61aa37e453dc1f69636402c4acd9209e982201] PUSHDATA(65)[04342f88b24dddadfc31a42901263e3283278b3164b9f65ab5b2343a6e1e1f178cec9510e1798b4e61b9777ce398c1ad99b247b7b12febb811d3fb1e6c6cbe8844] 50.00 BTC
          outpoint:dabca401532eb607f42703a4db069e455272f1d0d1a7756988c208582cd5ff28:1 hash160:840f4ec7a52487ed99a1abbd86e9c86cd2be595f
     in   PUSHDATA(71)[30440220288ed2fd7e7bb43a452b7d12c1f5926baf90a8758d8b350d277dc880b0f733d002204a1ccae4ce59ab45c4dd318f9f02e2a042baa32d813d0c794c332567272762a001] PUSHDATA(65)[0415da5e21e90b6f106dc477250a758a80de4300bdb7bca8ac24e157bb7f2f9374bc486437a528b847b3d741f6549369348b00216d6d8d6118fd72c427d32cfe19] 50.00 BTC
          outpoint:a2f744d647992e41e401c48cd695d7e208e7e9391e0d0e0f666448d32470eec7:0 hash160:86b72ac261ab714ff0f142d8dd13f4c36ad1a657
     out  DUP HASH160 PUSHDATA(20)[1b596d739fc128f270b5d5eff194986a111ec288] EQUALVERIFY CHECKSIG 12.8749 BTC
     out  DUP HASH160 PUSHDATA(20)[86b72ac261ab714ff0f142d8dd13f4c36ad1a657] EQUALVERIFY CHECKSIG 12.8749 BTC
     out  DUP HASH160 PUSHDATA(20)[373c15c109edd90d39dbc1f9cb1578cd7d988266] EQUALVERIFY CHECKSIG 12.8749 BTC
     out  DUP HASH160 PUSHDATA(20)[b0f6b0f8fcad785120292a24a6b492dc7ab56ab9] EQUALVERIFY CHECKSIG 0.0006 BTC
     out  DUP HASH160 PUSHDATA(20)[ab4cf6b579c2f6bb60dc2311f346ad9e8805e972] EQUALVERIFY CHECKSIG 12.8749 BTC
     out  DUP HASH160 PUSHDATA(20)[3eef4fd341c73f125b8165f894b4e6eeb6a30de3] EQUALVERIFY CHECKSIG 12.8749 BTC
     out  DUP HASH160 PUSHDATA(20)[bf79601e126c46d91e3f7ae31aec345785e3f33b] EQUALVERIFY CHECKSIG 12.8749 BTC
     out  DUP HASH160 PUSHDATA(20)[0bd7284499b641a15ac5203ca1358288600b7151] EQUALVERIFY CHECKSIG 12.8749 BTC
     out  DUP HASH160 PUSHDATA(20)[840f4ec7a52487ed99a1abbd86e9c86cd2be595f] EQUALVERIFY CHECKSIG 12.8749 BTC
```

This is link to similar transaction: https://www.biteasy.com/testnet/transactions/15813fe6c9ec67bf42a25fff0c02fd0a1626408cff80788462716f6880725742 

##This is how it works:
1. Read Bitcoin private keys from input file
2. Calculate te sum of BTC stored on those addresses
3. Transfer coins between addresses so each of them will have as even amount of coins as possible

