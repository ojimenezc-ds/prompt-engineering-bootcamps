package com.bootcamps.Prompt.engineering.bootcamps.dto

import com.bootcamps.Prompt.engineering.bootcamps.model.*

data class CreateUserRequest(
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

data class UpdateUserAgeRequest(
    val age: Int
)
