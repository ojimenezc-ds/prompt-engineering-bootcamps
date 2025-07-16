package com.bootcamps.Prompt.engineering.bootcamps.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    var age: Int,
    val gender: String? = null,
    val email: String? = null
)
