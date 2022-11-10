package swe.dependencies.consumer.info.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */
@Configuration
class RestTemplateConfig {

    @Bean
    @Suppress("HttpUrlsUsage")
    fun customerRestTemplate(
        @Value("\${provider.customer.address}") address: String,
        @Value("\${provider.customer.port}") port: String,
    ): RestTemplate {
        return RestTemplateBuilder().rootUri("http://$address:$port").build()
    }
}

fun RestTemplate.rootUri(): String {
    return this.uriTemplateHandler.expand("/").toString()
}