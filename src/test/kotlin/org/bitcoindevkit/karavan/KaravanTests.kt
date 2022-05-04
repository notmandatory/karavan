package org.bitcoindevkit.karavan

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.Assert
import org.springframework.web.context.WebApplicationContext
import java.lang.Exception
import javax.servlet.http.Cookie
import kotlin.random.Random

@SpringBootTest
class KaravanTests {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext
    lateinit var mockMVC: MockMvc
    val cookie1: Cookie = Cookie("descriptor", "d3NoKG11bHRpKDIsWzI3Mjk1Yjk4Lzg0Jy8xJy8wJy8wXXRwdWJERjZNSGhoaHJXVUx5Y1BGTU05UmkzRHFBYVpVZ3ROMTlOdWY1Y1Z6NVhWVG5xcWlUNTZCV0ZuZlA5ZW01UjUyNmdDSmpRVTY4Nnc3NzhLNVJqaFpmeHRNWmg3N1dETWYxcllVNnpTU3lxMy8qLFs3NzYwNjAzOC84NCcvMScvMCcvMF10cHViREVqVTFLS2JMQzNpTDgyaUg5RnFOZ2o3bmtEVFdOWDk2a3Q2dlBTTVk0a2dkWFVneVR3ZGVhTmZDQlJqQlhCaWtINHRSRW1mNTkxMkFUZ1JuYmZpVUpvcXBTcXFnSnR5NVhUOTlFQmRqNWkvKixbMGQ5MDM3ODIvODQnLzEnLzAnLzBddHB1YkRGMUF4OWMxNXVGNkRMNnJ1RnJ1a0dnTlNvZUtzdFZ1NzhrdFNFQTVDSEtib3FMRmQxRnFaTXBuQjNySGR2NUFUbnhlclN0NlRQTlUxemE1TDVhaDRwVkZGcDdDcGg4c0tIWDZvVlNHMVNrLyopKSN1ZHZ3OGgydg==")
    val cookie2: Cookie = Cookie("network", "TESTNET")

    @BeforeEach
    fun initialize() {
        mockMVC = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun testOpenWallet() {
        val wallet =  "{\"network\": \"TESTNET\",\"descriptor\": \"d3NoKG11bHRpKDIsWzI3Mjk1Yjk4Lzg0Jy8xJy8wJy8wXXRwdWJERjZNSGhoaHJXVUx5Y1BGTU05UmkzRHFBYVpVZ3ROMTlOdWY1Y1Z6NVhWVG5xcWlUNTZCV0ZuZlA5ZW01UjUyNmdDSmpRVTY4Nnc3NzhLNVJqaFpmeHRNWmg3N1dETWYxcllVNnpTU3lxMy8qLFs3NzYwNjAzOC84NCcvMScvMCcvMF10cHViREVqVTFLS2JMQzNpTDgyaUg5RnFOZ2o3bmtEVFdOWDk2a3Q2dlBTTVk0a2dkWFVneVR3ZGVhTmZDQlJqQlhCaWtINHRSRW1mNTkxMkFUZ1JuYmZpVUpvcXBTcXFnSnR5NVhUOTlFQmRqNWkvKixbMGQ5MDM3ODIvODQnLzEnLzAnLzBddHB1YkRGMUF4OWMxNXVGNkRMNnJ1RnJ1a0dnTlNvZUtzdFZ1NzhrdFNFQTVDSEtib3FMRmQxRnFaTXBuQjNySGR2NUFUbnhlclN0NlRQTlUxemE1TDVhaDRwVkZGcDdDcGg4c0tIWDZvVlNHMVNrLyopKSN1ZHZ3OGgydg==\"}"
        val expected = "Wallet is opened!\n"

        mockMVC.perform(put("/wallet")
            .contentType(MediaType.APPLICATION_JSON)
            .content(wallet))
            .andExpect(status().isOk)
            .andExpect(content().string(expected))
            .andExpect(cookie().value("descriptor", "d3NoKG11bHRpKDIsWzI3Mjk1Yjk4Lzg0Jy8xJy8wJy8wXXRwdWJERjZNSGhoaHJXVUx5Y1BGTU05UmkzRHFBYVpVZ3ROMTlOdWY1Y1Z6NVhWVG5xcWlUNTZCV0ZuZlA5ZW01UjUyNmdDSmpRVTY4Nnc3NzhLNVJqaFpmeHRNWmg3N1dETWYxcllVNnpTU3lxMy8qLFs3NzYwNjAzOC84NCcvMScvMCcvMF10cHViREVqVTFLS2JMQzNpTDgyaUg5RnFOZ2o3bmtEVFdOWDk2a3Q2dlBTTVk0a2dkWFVneVR3ZGVhTmZDQlJqQlhCaWtINHRSRW1mNTkxMkFUZ1JuYmZpVUpvcXBTcXFnSnR5NVhUOTlFQmRqNWkvKixbMGQ5MDM3ODIvODQnLzEnLzAnLzBddHB1YkRGMUF4OWMxNXVGNkRMNnJ1RnJ1a0dnTlNvZUtzdFZ1NzhrdFNFQTVDSEtib3FMRmQxRnFaTXBuQjNySGR2NUFUbnhlclN0NlRQTlUxemE1TDVhaDRwVkZGcDdDcGg4c0tIWDZvVlNHMVNrLyopKSN1ZHZ3OGgydg=="))
            .andExpect(cookie().value("network", "TESTNET"))
    }

    @Test
    fun testCloseUnopenWallet() {
        val noCookieExpected = "Wallet not found!\n"

        //delete an unopen wallet
        mockMVC.perform(delete("/wallet"))
            .andExpect(status().isOk)
            .andExpect(content().string(noCookieExpected))
            .andExpect(cookie().doesNotExist("descriptor"))
            .andExpect(cookie().doesNotExist("network"))
    }

    @Test
    fun testCloseOpenWallet() {
        val deleteExpected = "Wallet is closed!\n"

        mockMVC.perform(delete("/wallet")
            .cookie(cookie1, cookie2))
            .andExpect(status().isOk)
            .andExpect(content().string(deleteExpected))
            .andExpect(cookie().maxAge("descriptor",0)) //maxAge == 0 means the cookie is expired
            .andExpect(cookie().maxAge("network", 0))
    }


    //Getting the balance of an existing wallet
    @Test
    fun testGetBalance() {
        val result = mockMVC.perform(get("/wallet/balance")
            .cookie(cookie1,cookie2))
            .andExpect(status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        Assert.isTrue("balance" in content, "invalid balance found") // different balance for each wallet, so just check whether the response contains "balance"
    }


    //Getting the new address of valid cookies
    @Test
    fun testGetNewAddress() {
        val result = mockMVC.perform(get("/wallet/address/new")
            .cookie(cookie1,cookie2))
            .andExpect(status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        //Check that address is not empty
        Assert.isTrue(content.isNotEmpty())
    }

    //Getting the transactions of valid cookies
    @Test
    fun testGetTransactions() {
        val result = mockMVC.perform(get("/wallet/transactions")
            .cookie(cookie1,cookie2))
            .andExpect(status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        //check that the transactions are not empty
        Assert.isTrue(content.isNotEmpty())

    }

    //Testing Create unsigned PSBT API
    @Test
    fun testCreateUnsignedPSBT() {

        val result = mockMVC.perform(get("/wallet/transaction?recipient=tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt&amount=4500&fee_rate=1")
            .cookie(cookie1, cookie2))
            .andExpect(status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        // Verify that PSBT object was created and returned
        Assert.isTrue(content.isNotEmpty())
    }

    //Testing broadcast API
    @Test
    fun testBroadcast() {
        lateinit var msg: String

        //To do : Resolve errors: empty witness RPC Error: -26 / Transaction already in blockchain
        try {
            val result = mockMVC.perform(put("/wallet/broadcast")
                .contentType(MediaType.TEXT_PLAIN)
                .content("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")
                .cookie(cookie1, cookie2))
                .andExpect(status().isOk)
                .andReturn()
        }
        catch (e: Exception) {
            msg = e.message.toString()
            Assert.isTrue( "Transaction already in block chain" in msg, "Broadcast failed")
        }

    }
}



