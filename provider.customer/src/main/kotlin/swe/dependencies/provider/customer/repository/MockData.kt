package swe.dependencies.provider.customer.repository

import swe.dependencies.provider.customer.model.Address
import swe.dependencies.provider.customer.model.Customer
import swe.dependencies.provider.customer.model.FinancialProduct
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */

fun createCustomerMockData(): List<Customer> {
    // Products
    val productA = FinancialProduct(
        UUID.fromString("550e8400-e29b-11d4-a716-446655440000"),
        "A", BigDecimal(100), BigDecimal(5)
    )
    val productB = FinancialProduct(
        UUID.fromString("550e8400-e29b-11d4-a716-446655440001"),
        "B", BigDecimal(1000), BigDecimal(1)
    )
    val productC = FinancialProduct(
        UUID.fromString("550e8400-e29b-11d4-a716-446655440002"),
        "C", BigDecimal(500), BigDecimal(3.5)
    )
    val productC2 = FinancialProduct(
        UUID.fromString("550e8400-e29b-11d4-a716-446655440002"),
        "C", BigDecimal(200), BigDecimal(1.5)
    )
    // Addresses
    val addressA = Address(
        "Höchstädtpl. 6", "Vienna", "1200", "AT"
    )
    val addressB = Address(
        "Friedrich-Schmidt-Platz 1", "Vienna", "1010", "AT"
    )
    val addressC = Address(
        "Gablenzg. 11", "Vienna", "1150", "AT"
    )
    // Customers
    val customerA = Customer(
        "customer.a@gmail.com", "Max", "Mustermann",
        LocalDate.of(2000, 1, 1), addressA,
        listOf(productA)
    )
    val customerB = Customer(
        "customer.b@gmail.com", "Maxine", "Mustermann",
        LocalDate.of(2001, 1, 1), addressB,
        listOf(productB)
    )
    val customerC = Customer(
        "customer.c@gmail.com", "John", "Doe",
        LocalDate.of(1999, 1, 1), addressC,
        listOf(productC, productC2)
    )
    return listOf(customerA, customerB, customerC)
}