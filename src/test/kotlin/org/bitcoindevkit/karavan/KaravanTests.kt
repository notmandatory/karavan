package org.bitcoindevkit.karavan

import io.mockk.InternalPlatformDsl.toStr
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@AutoConfigureMockMvc
@SpringBootTest
class KaravanTests {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext
    lateinit var mockMVC: MockMvc

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
}


