package org.bitcoindevkit.karavan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.bitcoindevkit.*
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@SpringBootApplication
class Karavan

fun main(args: Array<String>) {
    runApplication<Karavan>(*args)
}

@RestController
@RequestMapping("/wallet")
class WalletController(val walletService: WalletService) {

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

    @PutMapping("/open_wallet")
    fun openWallet(response: HttpServletResponse, request: HttpServletRequest, @RequestBody payload: Wallet): String? {

        walletService.openWallet(response, payload)
        return walletService.readCookie(request, "wallet")
    }

}
