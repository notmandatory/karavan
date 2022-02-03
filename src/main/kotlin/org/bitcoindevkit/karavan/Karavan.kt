package org.bitcoindevkit.karavan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import org.bitcoindevkit.*
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

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

    @PutMapping("/open-wallet")
    fun setCookie(response: HttpServletResponse, @RequestBody descriptor:String): String? {
        // create a cookie
        val cookie = Cookie("wallet", descriptor) // might need to change to springframework cookie instead of java
        cookie.isHttpOnly = true

        //add cookie to response
        response.addCookie(cookie)
        return "Wallet Value Set!"
    }

    @GetMapping("/read-cookie")
    fun readCookie(@CookieValue(value = "wallet", defaultValue = "na") descriptor: String): String? {
        return "My wallet value is $descriptor"
    }
}
