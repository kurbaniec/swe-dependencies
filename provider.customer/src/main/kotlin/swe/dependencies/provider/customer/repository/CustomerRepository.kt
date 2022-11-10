package swe.dependencies.provider.customer.repository

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import swe.dependencies.provider.customer.model.Customer
import javax.annotation.PostConstruct

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */
@Repository
class CustomerRepository {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CustomerRepository::class.java)
    }

    private val customers = mutableMapOf<String, Customer>()

    fun getAll(): List<Customer> {
        logger.info("Querying all customers")
        return customers.values.toList()
    }

    fun getByEmail(email: String): Customer? {
        logger.info("Querying customer with email [$email]")
        return customers[email]
    }

    @PostConstruct
    private fun loadData() {
        logger.info("Loading mock data")
        val mockCustomers = createCustomerMockData()
            .map { c -> Pair(c.email, c) }
        customers.putAll(mockCustomers)
        logger.info("Mock data loaded")
    }
}