package org.bitcoindevkit.karavan

import org.bitcoindevkit.Network
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.util.WebUtils
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.full.memberProperties

/**
 * @suppress
 */
@SpringBootApplication
class Karavan
/**
 * @suppress
 */
fun main(args: Array<String>) {
    runApplication<Karavan>(*args)
}

/**
 * @suppress
 */
@Configuration
class CorsConfiguration : WebMvcConfigurer { // Needed to allow requests from the frontend
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS")
            .allowCredentials(true)
    }
}

/**
 * The controller class of the SpringBoot Project
 * @constructor Takes in a WallerService instance
 */
@RestController
@RequestMapping("/wallet")
class WalletController(val walletService: WalletService) {

    /**
     * Opens Wallet by storing the wallet payload in a browser session cookie
     * @see WalletService.validateDescriptor
     * @param request HttpServletRequest that contains cookies
     * @param payload Wallet of the corresponding user
     * @return A message that notifies the user whether the wallet is opened
     */
    @PutMapping
    fun openWallet(response: HttpServletResponse, request: HttpServletRequest, @RequestBody payload: Wallet): String? {

        // Validate Descriptor and Network before storing in cookie
        walletService.validateDescriptor(walletService.decodeBase64(payload.descriptor), payload.network)

        // *** If no exception is thrown in the code above, execution will continue here ***

        // stores payload of type wallet into a cookie
        setCookie(response, payload)
        return "Wallet is opened!\n"

    }

    // @TODO see why cookie is not closing properly.
    /**
     *  Closes wallet by dropping existing descriptor cookie
     *  @param request A HttpServeltRequest that contains cookies
     *  @return A message that notifies the user whether the wallet is closed
     */
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
                if (cookie.value == "")
                    return "Please specify a wallet to close"

                val cookieReplacement = Cookie(cookie.name, null)
                cookieReplacement.path = "/wallet"
                cookieReplacement.isHttpOnly = true
                cookieReplacement.maxAge = 0
                response.addCookie(cookieReplacement)
            }
            "Wallet is closed!\n"
        }
    }

    /**
     * Gets the wallet's balance
     * Use the browser cookie to get the network and descriptor and then syncing wallet
     * @return A balance json with the balance amount.
     */
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

    /**
     * Retrieves the address of the wallet when given cookies
     * @see WalletService.getNewAddress
     * @param request A HttpServletRequest that contains cookies
     * @return An address for the wallet
     */
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

    /**
     * Retrieves the past transactions completed in the wallet
     * @param request A HttpServletRequest that contains cookies
     * @return A JSON array of transactions of the wallet
     */
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
    /**
     * Creates partially signed bitcoin transaction for the wallet
     * @param request A HttpServletRequest that contains cookies
     * @param recipient The recipient who receives the transaction
     * @param amount The amount of bitcoin that is to be trasnsferred
     * @param fee_rate The fee_rate for the transaction
     * @return A PSBT object
     */
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

    /**
     * Broadcasts the signed transaction to the global bitcoin blockchain
     * @param request A HttpServletRequest that contains cookies
     * @param payload A serialized psbt for the transaction
     * @return Network address with the transaction id
     */
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

    /**
     * Stores wallet object into client's cookie session
     * @param walletIn Wallet object that will be stored into the client's session
     */
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

    /**
     * Reads a single cookie value based on key input
     * @param request A HttpServletRequest that contains cookies
     * @param key A key for one of the cookies
     * @return Value of the wallet for the cookie with the key
     */
    fun readCookie(request: HttpServletRequest?, key: String): String? {

        val cookie = WebUtils.getCookie(request!!, key)
        return if (cookie != null) {
            "Wallet value is ${cookie.value}\n"
        } else {
            "Wallet not found!\n"
        }
    }

    /**
     * Reads all cookie inside the client's web session
     * @param request A HttpServletRequest that contains cookies
     */
    fun readAllCookies(request: HttpServletRequest?): String? {

        val cookies = request!!.cookies
        return if (cookies != null) {
            Arrays.stream(cookies)
                .map { c -> c.getName() + "=" + c.getValue() }.collect(Collectors.joining(", "))
        } else {
            "No cookies\n"
        }
    }

    /**
     * Retrieves the cookies of the wallert
     * @param request A HttpServletRequest that contains cookies
     * @return Wallet object with key-value pairs inside
     */
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
