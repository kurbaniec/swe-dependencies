package swe.dependencies.consumer.info

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.web.client.RestTemplateBuilder
import swe.dependencies.consumer.info.model.CustomerInfo
import swe.dependencies.consumer.info.repository.CustomerProviderAPI
import swe.dependencies.consumer.info.service.InfoService
import kotlin.test.assertEquals


/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */
class InfoServiceTest {

    private lateinit var wireMockServer: WireMockServer
    private lateinit var infoService: InfoService

    @BeforeEach
    fun setUp() {
        wireMockServer = WireMockServer(WireMockConfiguration.options().dynamicPort())
        wireMockServer.start()
        val restTemplate = RestTemplateBuilder()
            .rootUri(wireMockServer.baseUrl())
            .build()
        val api = CustomerProviderAPI(restTemplate)
        infoService = InfoService(api)
    }

    @AfterEach
    fun tearDown() {
        wireMockServer.stop()
    }

    @Test
    fun `Query all consumer info`() {
        wireMockServer
            .stubFor(
                get(urlPathEqualTo("/customers"))
                    .willReturn(
                        aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(consumerData)
                    )
            )
        val expected = listOf(
            CustomerInfo("Max Mustermann", "customer.a@gmail.com", "CREATED"),
            CustomerInfo("Maxine Mustermann", "customer.b@gmail.com", "VERIFIED"),
            CustomerInfo("John Doe", "customer.c@gmail.com", "BANNED")
        )

        val info = infoService.getAllCustomerInfo()

        assertEquals(expected, info)
    }


    companion object Data {
        const val consumerData = "[\n" +
                "    {\n" +
                "        \"email\": \"customer.a@gmail.com\",\n" +
                "        \"firstname\": \"Max\",\n" +
                "        \"lastname\": \"Mustermann\",\n" +
                "        \"birthday\": \"2000-01-01\",\n" +
                "        \"address\": {\n" +
                "            \"street\": \"Höchstädtpl. 6\",\n" +
                "            \"city\": \"Vienna\",\n" +
                "            \"zip\": \"1200\",\n" +
                "            \"country\": \"AT\"\n" +
                "        },\n" +
                "        \"products\": [\n" +
                "            {\n" +
                "                \"code\": \"550e8400-e29b-11d4-a716-446655440000\",\n" +
                "                \"name\": \"A\",\n" +
                "                \"balance\": 100,\n" +
                "                \"interestRate\": 5\n" +
                "            }\n" +
                "        ],\n" +
                "        \"status\": \"CREATED\",\n" +
                "        \"name\": \"Max Mustermann\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"email\": \"customer.b@gmail.com\",\n" +
                "        \"firstname\": \"Maxine\",\n" +
                "        \"lastname\": \"Mustermann\",\n" +
                "        \"birthday\": \"2001-01-01\",\n" +
                "        \"address\": {\n" +
                "            \"street\": \"Friedrich-Schmidt-Platz 1\",\n" +
                "            \"city\": \"Vienna\",\n" +
                "            \"zip\": \"1010\",\n" +
                "            \"country\": \"AT\"\n" +
                "        },\n" +
                "        \"products\": [\n" +
                "            {\n" +
                "                \"code\": \"550e8400-e29b-11d4-a716-446655440001\",\n" +
                "                \"name\": \"B\",\n" +
                "                \"balance\": 1000,\n" +
                "                \"interestRate\": 1\n" +
                "            }\n" +
                "        ],\n" +
                "        \"status\": \"VERIFIED\",\n" +
                "        \"name\": \"Maxine Mustermann\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"email\": \"customer.c@gmail.com\",\n" +
                "        \"firstname\": \"John\",\n" +
                "        \"lastname\": \"Doe\",\n" +
                "        \"birthday\": \"1999-01-01\",\n" +
                "        \"address\": {\n" +
                "            \"street\": \"Gablenzg. 11\",\n" +
                "            \"city\": \"Vienna\",\n" +
                "            \"zip\": \"1150\",\n" +
                "            \"country\": \"AT\"\n" +
                "        },\n" +
                "        \"products\": [\n" +
                "            {\n" +
                "                \"code\": \"550e8400-e29b-11d4-a716-446655440002\",\n" +
                "                \"name\": \"C\",\n" +
                "                \"balance\": 500,\n" +
                "                \"interestRate\": 3.5\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"550e8400-e29b-11d4-a716-446655440002\",\n" +
                "                \"name\": \"C\",\n" +
                "                \"balance\": 200,\n" +
                "                \"interestRate\": 1.5\n" +
                "            }\n" +
                "        ],\n" +
                "        \"status\": \"BANNED\",\n" +
                "        \"name\": \"John Doe\"\n" +
                "    }\n" +
                "]"
    }
}