package swe.dependencies.consumer.info.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import swe.dependencies.consumer.info.model.CustomerInfo
import swe.dependencies.consumer.info.service.InfoService

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */
@RestController
class InfoController(
    private val service: InfoService
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(InfoController::class.java)
    }

    @GetMapping("/customers", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllCustomerInfo(): ResponseEntity<List<CustomerInfo>> {
        return runCatching { service.getAllCustomerInfo() }
            .map { customers -> ResponseEntity.ok(customers) }
            .onFailure { e -> logger.error(e.stackTraceToString()) }
            .getOrElse { ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR) }
    }
}