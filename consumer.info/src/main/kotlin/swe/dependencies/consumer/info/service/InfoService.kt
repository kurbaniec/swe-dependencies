package swe.dependencies.consumer.info.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import swe.dependencies.consumer.info.model.CustomerInfo
import swe.dependencies.consumer.info.repository.CustomerProviderAPI

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */
@Service
class InfoService(
    private val api: CustomerProviderAPI
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(InfoService::class.java)
    }

    fun getAllCustomerInfo(): List<CustomerInfo> {
        logger.info("Querying customer info")
        val customers = api.getAll()
        logger.info("Found ${customers.count()}")
        return customers
    }
}