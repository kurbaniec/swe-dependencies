package swe.dependencies.consumer.info

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonArrayMinLike
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.PactSpecVersion
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
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
@ExtendWith(PactConsumerTestExt::class)
class InfoConsumerPactTest {

    @Pact(consumer = "InfoService", provider = "CustomerService")
    fun getAllCustomers(builder: PactDslWithProvider): RequestResponsePact {
        return builder.given("customers exist")
            .uponReceiving("get all customers")
            .method("GET")
            .path("/customers")
            .willRespondWith()
            .status(200)
            .headers(mapOf("Content-Type" to "application/json"))
            .body(newJsonArrayMinLike(1) { array ->
                array.`object` { o ->
                    o.stringType("name", "Max Mustermann")
                    o.stringType("email", "customer.a@gmail.com")
                    o.stringType("status", "CREATED")
                }
            }.build())
            .toPact()
    }

    @Pact(consumer = "InfoService", provider = "CustomerService")
    fun noCustomersExist(builder: PactDslWithProvider): RequestResponsePact {
        return builder.given("no customers exist")
            .uponReceiving("get all customers")
            .method("GET")
            .path("/customers")
            .willRespondWith()
            .status(200)
            .headers(mapOf("Content-Type" to "application/json"))
            .body("[]")
            .toPact()
    }

    @Test
    @PactTestFor(pactMethod = "getAllCustomers", pactVersion = PactSpecVersion.V3)
    fun getAllCustomers_whenCustomersExist(mockServer: MockServer) {
        val expected = listOf(CustomerInfo("Max Mustermann", "customer.a@gmail.com", "CREATED"))
        val restTemplate = RestTemplateBuilder()
            .rootUri(mockServer.getUrl())
            .build()
        val infoService = InfoService(CustomerProviderAPI(restTemplate))

        val info = infoService.getAllCustomerInfo()

        assertEquals(expected, info)
    }

    @Test
    @PactTestFor(pactMethod = "noCustomersExist", pactVersion = PactSpecVersion.V3)
    fun getAllCustomers_whenNoCustomersExist(mockServer: MockServer) {
        val expected = emptyList<CustomerInfo>()
        val restTemplate = RestTemplateBuilder()
            .rootUri(mockServer.getUrl())
            .build()
        val infoService = InfoService(CustomerProviderAPI(restTemplate))

        val info = infoService.getAllCustomerInfo()

        assertEquals(expected, info)
    }
}