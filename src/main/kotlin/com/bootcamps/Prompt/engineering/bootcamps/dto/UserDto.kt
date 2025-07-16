package com.bootcamps.Prompt.engineering.bootcamps.dto

data class CreateUserRequest(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val gender: String? = null,
    val email: String? = null
)

data class UpdateAgeRequest(
    val age: Int
)
