package com.bootcamps.Prompt.engineering.bootcamps.model

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val maidenName: String?,
    val age: Int,
    val gender: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String,
    val birthDate: String,
    val image: String,
    val bloodGroup: String,
    val height: Double,
    val weight: Double,
    val eyeColor: String,
    val hair: Hair,
    val ip: String,
    val address: Address,
    val macAddress: String,
    val university: String,
    val bank: Bank,
    val company: Company,
    val ein: String,
    val ssn: String,
    val userAgent: String,
    val crypto: Crypto,
    val role: String
)

data class Hair(
    val color: String?,
    val type: String
)

data class Address(
    val address: String?,
    val city: String?,
    val state: String?,
    val stateCode: String?,
    val postalCode: String?,
    val coordinates: Coordinates?,
    val country: String
)

data class Coordinates(
    val lat: Double,
    val lng: Double
)

data class Bank(
    val cardExpire: String?,
    val cardNumber: String?,
    val cardType: String?,
    val currency: String?,
    val iban: String
)

data class Company(
    val department: String?,
    val name: String?,
    val title: String?,
    val address: Address?
)

data class Crypto(
    val coin: String?,
    val wallet: String?,
    val network: String
)

data class UsersResponse(
    val users: List<User>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class CreateUserRequest(
    val firstName: String,
    val lastName: String,
    val maidenName: String? = null,
    val age: Int,
    val gender: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String,
    val birthDate: String,
    val image: String? = null,
    val bloodGroup: String? = null,
    val height: Double? = null,
    val weight: Double? = null,
    val eyeColor: String? = null,
    val hair: Hair? = null,
    val ip: String? = null,
    val address: Address? = null,
    val macAddress: String? = null,
    val university: String? = null,
    val bank: Bank? = null,
    val company: Company? = null,
    val ein: String? = null,
    val ssn: String? = null,
    val userAgent: String? = null,
    val crypto: Crypto? = null,
    val role: String = "user"
)
