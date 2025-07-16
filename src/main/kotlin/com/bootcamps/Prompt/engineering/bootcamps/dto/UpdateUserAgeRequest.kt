package com.bootcamps.Prompt.engineering.bootcamps.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class UpdateUserAgeRequest(
    @field:NotNull(message = "Age is required")
    @field:Positive(message = "Age must be positive")
    val age: Int
)
