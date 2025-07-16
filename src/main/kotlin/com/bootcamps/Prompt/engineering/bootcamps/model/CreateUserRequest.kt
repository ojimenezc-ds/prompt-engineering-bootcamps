package com.bootcamps.Prompt.engineering.bootcamps.model

data class CreateUserRequest(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val gender: String,
    val email: String,
    val phone: String,
    val username: String
)

data class UpdateUserAgeRequest(
    val age: Int
)
