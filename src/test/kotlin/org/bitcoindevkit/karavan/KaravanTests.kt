package org.bitcoindevkit.karavan

import com.jayway.jsonpath.internal.Utils.notNull
import org.json.JSONObject
import org.json.JSONString
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.util.AssertionErrors.assertNotNull
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession
import org.springframework.util.Assert
import org.springframework.web.context.WebApplicationContext
import java.util.*
import javax.servlet.http.HttpSession
import org.junit.jupiter.api.Assertions
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.web.servlet.request.RequestPostProcessor
import java.util.regex.Matcher
import javax.servlet.http.Cookie

@SpringBootTest
class KaravanTests {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext
    lateinit var mockMVC: MockMvc
    val invalidCookie1: Cookie? = Cookie("descriptor", null)
    val invalidCookie2: Cookie? = Cookie("network", null)
    val cookie1: Cookie = Cookie("descriptor", "wpkh([1f44db3b/84'/1'/0'/0]tpubDEtS2joSaGheeVGuopWunPzqi7D3BJ9kooggvasZWUzSVziMNKkrdfS7VnLDe6M4Cg6bw3j5oxRB5U7GMJGcFnDia6yUaFAdwWqyJQjn4Qp/0/*)")
    val cookie2: Cookie = Cookie("network", "testnet")

    @BeforeEach
    fun initialize() {
        mockMVC = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun testOpenWallet() {
        val wallet =  "{\"network\": \"testnet\",\"descriptor\": \"wpkh([1f44db3b/84'/1'/0'/0]tpubDEtS2joSaGheeVGuopWunPzqi7D3BJ9kooggvasZWUzSVziMNKkrdfS7VnLDe6M4Cg6bw3j5oxRB5U7GMJGcFnDia6yUaFAdwWqyJQjn4Qp/0/*)\"}"
        val expected = "Wallet is opened!\n"

        mockMVC.perform(put("/wallet")
            .contentType(MediaType.APPLICATION_JSON)
            .content(wallet))
            .andExpect(status().isOk)
            .andExpect(content().string(expected))
            .andExpect(cookie().value("descriptor", "wpkh([1f44db3b/84'/1'/0'/0]tpubDEtS2joSaGheeVGuopWunPzqi7D3BJ9kooggvasZWUzSVziMNKkrdfS7VnLDe6M4Cg6bw3j5oxRB5U7GMJGcFnDia6yUaFAdwWqyJQjn4Qp/0/*)"))
            .andExpect(cookie().value("network", "testnet"))
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

    //Getting the balance of an unexisting wallet
    @Test
    fun testInvalidGetBalance() {
        val expected = "Wallet not found.\n"

        mockMVC.perform(get("/wallet/balance")
            .cookie(invalidCookie1, invalidCookie2))
            .andExpect(status().isOk)
            .andExpect(content().string(expected))
            .andExpect(cookie().doesNotExist("descriptor"))
            .andExpect(cookie().doesNotExist("network"))
    }

    //Getting the balance of an existing wallet
    @Test
    fun testValidGetBalance() {
        val result = mockMVC.perform(get("/wallet/balance")
            .cookie(cookie1,cookie2))
            .andExpect(status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        Assert.isTrue("balance" in content, "invalid balance found") // different balance for each wallet, so just check whether the response contains "balance"
    }

    //Getting the new address of invalid cookies
    @Test
    fun testInvalidGetNewAddress() {
        val expected = "Wallet not found.\n"

        mockMVC.perform(get("/wallet/address/new")
            .cookie(invalidCookie1, invalidCookie2))
            .andExpect(status().isOk)
            .andExpect(content().string(expected))
            .andExpect(cookie().doesNotExist("descriptor"))
            .andExpect(cookie().doesNotExist("network"))

    }

    //Getting the new address of valid cookies
    @Test
    fun testValidGetNewAddress() {
        val result = mockMVC.perform(get("/wallet/address/new")
            .cookie(cookie1,cookie2))
            .andExpect(status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        //Check that address is not empty
        Assert.isTrue(content.isNotEmpty())
    }

    //Getting the transactions of invalid cookies
    @Test
    fun testInvalidGetTransactions() {
        val expected = "Wallet not found.\n"

        mockMVC.perform(get("/wallet/transactions")
            .cookie(invalidCookie1, invalidCookie2))
            .andExpect(status().isOk)
            .andExpect(content().string(expected))
            .andExpect(cookie().doesNotExist("descriptor"))
            .andExpect(cookie().doesNotExist("network"))
    }

    //Getting the transactions of valid cookies
    @Test
    fun testValidGetTransactions() {
        val result = mockMVC.perform(get("/wallet/transactions")
            .cookie(cookie1,cookie2))
            .andExpect(status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        //check that the transactions are not empty
        Assert.isTrue(content.isNotEmpty())

    }
}



