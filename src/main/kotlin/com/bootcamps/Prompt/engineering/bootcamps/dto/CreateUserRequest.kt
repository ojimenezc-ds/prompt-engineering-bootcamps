package com.bootcamps.Prompt.engineering.bootcamps.dto

import com.bootcamps.Prompt.engineering.bootcamps.model.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class CreateUserRequest(
    @field:NotBlank(message = "First name is required")
    val firstName: String,
    
    @field:NotBlank(message = "Last name is required")
    val lastName: String,
    
    val maidenName: String? = null,
    
    @field:NotNull(message = "Age is required")
    @field:Positive(message = "Age must be positive")
    val age: Int,
    
    @field:NotBlank(message = "Gender is required")
    val gender: String,
    
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email must be valid")
    val email: String,
    
    @field:NotBlank(message = "Phone is required")
    val phone: String,
    
    @field:NotBlank(message = "Username is required")
    val username: String,
    
    @field:NotBlank(message = "Password is required")
    val password: String,
    
    @field:NotBlank(message = "Birth date is required")
    val birthDate: String,
    
    val image: String = "",
    val bloodGroup: String = "",
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val eyeColor: String = "",
    val hair: Hair = Hair(color = null, type = ""),
    val ip: String = "",
    val address: Address = Address(
        address = null,
        city = null,
        state = null,
        stateCode = null,
        postalCode = null,
        coordinates = null,
        country = ""
    ),
    val macAddress: String = "",
    val university: String = "",
    val bank: Bank = Bank(
        cardExpire = null,
        cardNumber = null,
        cardType = null,
        currency = null,
        iban = ""
    ),
    val company: Company = Company(
        department = null,
        name = null,
        title = null,
        address = null
    ),
    val ein: String = "",
    val ssn: String = "",
    val userAgent: String = "",
    val crypto: Crypto = Crypto(
        coin = null,
        wallet = null,
        network = ""
    ),
    val role: String = "user"
)
