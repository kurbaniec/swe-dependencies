package swe.dependencies.consumer.info.repository

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import swe.dependencies.consumer.info.config.rootUri
import swe.dependencies.consumer.info.model.CustomerInfo

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */
@Repository
class CustomerProviderAPI(
    private val restTemplate: RestTemplate
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CustomerProviderAPI::class.java)
    }

    private val customerListType = object : ParameterizedTypeReference<List<CustomerInfo>>() {}

    @Throws(Exception::class)
    fun getAll(): List<CustomerInfo> {
        logger.info("Querying customer data from [${restTemplate.rootUri()}]")
        return restTemplate.exchange(
            "/customers",
            HttpMethod.GET,
            HttpEntity<String>(HttpHeaders()),
            customerListType
        ).body ?: throw IllegalStateException("API did not return customers")
    }
}