package com.bootcamps.Prompt.engineering.bootcamps.dto

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
    
    val image: String? = null,
    
    val bloodGroup: String? = null,
    
    val height: Double? = null,
    
    val weight: Double? = null,
    
    val eyeColor: String? = null,
    
    val university: String? = null,
    
    val role: String = "user"
)
