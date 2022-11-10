package swe.dependencies.provider.customer.model

import java.time.LocalDate

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */
data class Customer(
    val email: String,
    val firstname: String,
    val lastname: String,
    val birthday: LocalDate,
    val address: Address,
    val products: List<FinancialProduct>,
    val status: CustomerStatus
) {
    val name: String get() = "$firstname $lastname"
}
