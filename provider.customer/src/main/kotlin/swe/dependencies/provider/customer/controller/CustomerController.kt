package swe.dependencies.provider.customer.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import swe.dependencies.provider.customer.model.Customer
import swe.dependencies.provider.customer.service.CustomerService

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */
@RestController
class CustomerController(
    private val service: CustomerService
) {

    @GetMapping("/customers", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllCustomers(): List<Customer> {
        return service.getAll()
    }

    @GetMapping("/customer/{email}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCustomerByEmail(@PathVariable email: String): ResponseEntity<Customer> {
        val customer = service.getByEmail(email)
        return if (customer != null) ResponseEntity.ok(customer)
        else ResponseEntity(HttpStatus.NOT_FOUND)
    }
}