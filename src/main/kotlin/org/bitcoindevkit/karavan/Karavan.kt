package org.bitcoindevkit.karavan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.bitcoindevkit.*

@SpringBootApplication
class Karavan

fun main(args: Array<String>) {
    runApplication<Karavan>(*args)
}

@RestController
@RequestMapping("/wallet")
class WalletController() {

    // TODO remove this example
    @GetMapping("/hello/{name}")
    fun hello(@PathVariable name: String): String {
        val descriptor = "wpkh([c258d2e4/84h/1h/0h]tpubDDYkZojQFQjht8Tm4jsS3iuEmKjTiEGjG6KnuFNKKJb5A6ZUCUZKdvLdSDWofKi4ToRCwb9poe1XdqfUnP4jaJjCB2Zwv11ZLgSbnZSNecE/0/*)"
        val db = DatabaseConfig.Memory("")

        val client =
            BlockchainConfig.Electrum(
                ElectrumConfig("ssl://electrum.blockstream.info:60002", null, 5u, null, 10u)
            )
        val wallet = OnlineWallet(descriptor, null, Network.TESTNET, db, client)
        val newAddress = wallet.getNewAddress()
        return "hello $name, your new address is $newAddress"
    }
}
