package swe.dependencies.provider.customer.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import swe.dependencies.provider.customer.model.Customer
import swe.dependencies.provider.customer.repository.CustomerRepository

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */
@Service
class CustomerService(
    private val repository: CustomerRepository
) {
    
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CustomerService::class.java)
    }
    
    fun getAll(): List<Customer> {
        logger.info("Querying all customers")
        val customers =  repository.getAll()
        logger.info("Found ${customers.count()}")
        return customers
    }
    
    fun getByEmail(email: String): Customer? {
        logger.info("Querying customer with email [$email]")
        val customer = repository.getByEmail(email)
        if (customer != null) logger.info("Found customer [${customer.email}]")
        else logger.info("Customer not found")
        return customer
    }
}