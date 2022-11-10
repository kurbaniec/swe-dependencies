package swe.dependencies.provider.customer.model

import java.math.BigDecimal
import java.util.UUID

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */
data class FinancialProduct(
    val code: UUID,
    val name: String,
    val balance: BigDecimal,
    val interestRate: BigDecimal
)
