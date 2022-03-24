package org.bitcoindevkit.karavan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.WebUtils
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.full.memberProperties


@SpringBootApplication
class Karavan

fun main(args: Array<String>) {
    runApplication<Karavan>(*args)
}

@RestController
@RequestMapping("/wallet")
class WalletController(val walletService: WalletService) {

    // Open Wallet by storing the wallet payload in a browser session cookie
    @PutMapping
    fun openWallet(response: HttpServletResponse, request: HttpServletRequest, @RequestBody payload: Wallet): String? {

        // @TODO need to validate input here for network and descriptor before storing them into cookie
        // stores payload of type wallet into a cookie
        setCookie(response, payload)
        return "Wallet is opened!\n"
    }

    // Close wallet by dropping existing descriptor cookie
    @DeleteMapping
    fun closeWallet(response: HttpServletResponse, request: HttpServletRequest): String{

        // Retrieve all cookies from request
        val cookies = request.cookies

        return if (cookies == null) {
            "Wallet not found!\n"
        }
        else {
            // In order to delete a cookie, we must replicate it, set its maxAge to 0 and add it to response
            for(cookie in cookies) {
                val cookieReplacement = Cookie(cookie.name, null)
                cookieReplacement.path = "/wallet"
                cookieReplacement.isHttpOnly = true
                cookie.maxAge = 0
                response.addCookie(cookieReplacement)
            }
            "Wallet is closed!\n"
        }
    }

    // Get the wallet's balance
    // Use the browser cookie to get the network and descriptor and then syncing wallet
    // return a balance json with the balance amount.
    @GetMapping("/balance")
    fun getBalance(request: HttpServletRequest): String{

        // Retrieve wallet values stored in cookies
        val wallet = getWalletCookies(request)

        // Call getBalance from WalletService class to process logic and return balance JSON
        return if (wallet != null) {
            walletService.getBalance(wallet.descriptor, wallet.network) + "\n"
        }
        else {
            "Wallet is null."
        }
    }

    @GetMapping("/address/new")
    fun getNewAddress(request: HttpServletRequest): String{

        // Retrieve wallet values stored in cookies
        val wallet = getWalletCookies(request)

        // Call getAddress from WalletService class to return an address in string format
        return if (wallet != null) {
            walletService.getNewAddress(wallet.descriptor, wallet.network) + "\n"
        }
        else {
            "Wallet is null."
        }
    }

    @GetMapping("/transactions")
    fun getTransactions(request: HttpServletRequest): String{

        // Retrieve wallet values stored in cookies
        val wallet = getWalletCookies(request)

        // Call getTransactions from WalletService class to return JSON array of transactions
        return if (wallet != null) {
            walletService.getTransactions(wallet.descriptor, wallet.network) + "\n"
        }
        else {
            "Wallet is null."
        }
    }

    // @TODO Create a function to validate parameter values
    @GetMapping("/transaction")
    fun createPSBT(request: HttpServletRequest, @RequestParam(value = "recipient") recipient: String, @RequestParam(value = "amount") amount: ULong,
                   @RequestParam(value = "fee_rate") fee_rate: Float): String{

        // Retrieve wallet values stored in cookies
        val wallet = getWalletCookies(request)

        // Call createUnsignedPSBT from WalletService class to return PSBT object serialized
        return if (wallet != null) {
            walletService.createUnsignedPSBT(wallet.descriptor, wallet.network, recipient, amount, fee_rate)
        }
        else {
            "Wallet is null."
        }
    }

    @PutMapping("/broadcast")
    fun broadcast(request: HttpServletRequest, @RequestBody payload: String): String{

        // Retrieve wallet values stored in cookies
        val wallet = getWalletCookies(request)

        // Call broadcastSignedPSBT from WalletService class to broadcast PSBT into the blockchain
        return if (wallet != null) {
            walletService.broadcastSignedPSBT(wallet.descriptor, wallet.network, payload)
        }
        else {
            "Wallet is null."
        }
    }

    // Store wallet object into client's cookie session
    fun setCookie(response: HttpServletResponse, walletIn: Wallet) {

        // create cookie key-value pairs for every wallet data member
        for (dataMember in Wallet::class.memberProperties) {
            val cookie = Cookie(dataMember.name, dataMember.get(walletIn) as String?)
            // set httpOnly to true so cookie can only be accessed by server (to prevent XSS attacks)
            cookie.isHttpOnly = true
            // set cookie scope to "/wallet"
            cookie.path = "/wallet"

            response.addCookie(cookie)
        }
    }

    // Read a single cookie value based on key input
    fun readCookie(request: HttpServletRequest?, key: String): String? {

        val cookie = WebUtils.getCookie(request!!, key)
        return if (cookie != null) {
            "Wallet value is ${cookie.value}\n"
        } else {
            "Wallet not found!\n"
        }
    }

    // Read all cookie inside the client's web session
    fun readAllCookies(request: HttpServletRequest?): String? {

        val cookies = request!!.cookies
        return if (cookies != null) {
            Arrays.stream(cookies)
                .map { c -> c.getName() + "=" + c.getValue() }.collect(Collectors.joining(", "))
        } else {
            "No cookies\n"
        }
    }

    fun getWalletCookies(request: HttpServletRequest) : Wallet? {

        // Retrieve wallet cookies
        val descCookie = WebUtils.getCookie(request, "descriptor")
        val networkCookie = WebUtils.getCookie(request, "network")

        // check if cookies are null or dropped
        if (descCookie == null || networkCookie == null || descCookie.value.isNullOrEmpty() || networkCookie.value.isNullOrEmpty()){
            // Throw IllegalAccessError Exception if wallet is not found or not valid
            throw IllegalAccessError("Wallet not found.\n")
        }

        // return wallet object with key-value pairs inside
        return Wallet(networkCookie.value, walletService.decodeBase64(descCookie.value));

    }

}
