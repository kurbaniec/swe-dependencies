package swe.dependencies.provider.customer.model

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-11-10
 */
data class Address(
  val street: String,
  val city: String,
  val zip: String,
  val country: String
)
