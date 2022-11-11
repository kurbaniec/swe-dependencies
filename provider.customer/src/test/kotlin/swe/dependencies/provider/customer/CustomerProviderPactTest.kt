package swe.dependencies.provider.customer

import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.State
import au.com.dius.pact.provider.junitsupport.loader.PactBroker
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth
import au.com.dius.pact.provider.junitsupport.loader.PactFolder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import swe.dependencies.provider.customer.repository.CustomerRepository
import swe.dependencies.provider.customer.repository.createCustomerMockData

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-11
 */

@Provider("CustomerService")
@PactFolder("pacts")
// Local pact contracts are used instead of broker
// @PactBroker(
//    host = "localhost",
//    port = "8000",
//    authentication = PactBrokerAuth(username = "pact_customer", password = "pact_customer")
// )
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerProviderPactTest {

    @LocalServerPort
    private var port: Int = 0

    @MockBean
    lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setUp(context: PactVerificationContext) {
        context.target = HttpTestTarget("localhost", port)
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    fun verifyPact(context: PactVerificationContext) {
        context.verifyInteraction()
    }

    @State("customers exist")
    fun toCustomersExistState() {
        `when`(customerRepository.getAll())
            .thenReturn(createCustomerMockData())
    }

    @State("no customers exist")
    fun toNoCustomersExistState() {
        `when`(customerRepository.getAll())
            .thenReturn(emptyList())
    }
}