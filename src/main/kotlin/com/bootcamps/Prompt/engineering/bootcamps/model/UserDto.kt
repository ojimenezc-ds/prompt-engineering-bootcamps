package com.bootcamps.Prompt.engineering.bootcamps.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.*

@Schema(description = "Data Transfer Object for creating a new user")
data class CreateUserDto @JsonCreator constructor(
    @JsonProperty("firstName")
    @field:NotBlank(message = "First name is required")
    @field:Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    @Schema(description = "User's first name", example = "John")
    val firstName: String,

    @JsonProperty("lastName")
    @field:NotBlank(message = "Last name is required")
    @field:Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    @Schema(description = "User's last name", example = "Doe")
    val lastName: String,

    @JsonProperty("maidenName")
    @Schema(description = "User's maiden name", example = "Smith")
    val maidenName: String? = null,

    @JsonProperty("age")
    @field:Min(value = 0, message = "Age must be at least 0")
    @field:Max(value = 150, message = "Age must be at most 150")
    @Schema(description = "User's age", example = "30")
    val age: Int,

    @JsonProperty("gender")
    @field:NotBlank(message = "Gender is required")
    @Schema(description = "User's gender", example = "male")
    val gender: String,

    @JsonProperty("email")
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email must be valid")
    @Schema(description = "User's email address", example = "john.doe@example.com")
    val email: String,

    @JsonProperty("phone")
    @field:NotBlank(message = "Phone is required")
    @Schema(description = "User's phone number", example = "+1 234-567-8900")
    val phone: String,

    @JsonProperty("username")
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @Schema(description = "User's username", example = "johndoe")
    val username: String,

    @JsonProperty("password")
    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    @Schema(description = "User's password", example = "securePassword123")
    val password: String,

    @JsonProperty("birthDate")
    @field:NotBlank(message = "Birth date is required")
    @Schema(description = "User's birth date", example = "1993-1-15")
    val birthDate: String,

    @JsonProperty("image")
    @Schema(description = "User's profile image URL", example = "https://example.com/profile.jpg")
    val image: String? = null,

    @JsonProperty("bloodGroup")
    @Schema(description = "User's blood group", example = "A+")
    val bloodGroup: String? = null,

    @JsonProperty("height")
    @field:DecimalMin(value = "0.0", message = "Height must be positive")
    @Schema(description = "User's height in cm", example = "175.5")
    val height: Double? = null,

    @JsonProperty("weight")
    @field:DecimalMin(value = "0.0", message = "Weight must be positive")
    @Schema(description = "User's weight in kg", example = "70.0")
    val weight: Double? = null,

    @JsonProperty("eyeColor")
    @Schema(description = "User's eye color", example = "Brown")
    val eyeColor: String? = null,

    @JsonProperty("hair")
    @field:Valid
    @Schema(description = "User's hair information")
    val hair: Hair? = null,

    @JsonProperty("ip")
    @Schema(description = "User's IP address", example = "192.168.1.1")
    val ip: String? = null,

    @JsonProperty("address")
    @field:Valid
    @Schema(description = "User's address information")
    val address: Address? = null,

    @JsonProperty("macAddress")
    @Schema(description = "User's MAC address", example = "00:11:22:33:44:55")
    val macAddress: String? = null,

    @JsonProperty("university")
    @Schema(description = "User's university", example = "Harvard University")
    val university: String? = null,

    @JsonProperty("bank")
    @field:Valid
    @Schema(description = "User's bank information")
    val bank: Bank? = null,

    @JsonProperty("company")
    @field:Valid
    @Schema(description = "User's company information")
    val company: Company? = null,

    @JsonProperty("ein")
    @Schema(description = "User's EIN", example = "12-3456789")
    val ein: String? = null,

    @JsonProperty("ssn")
    @Schema(description = "User's SSN", example = "123-45-6789")
    val ssn: String? = null,

    @JsonProperty("userAgent")
    @Schema(description = "User's user agent string")
    val userAgent: String? = null,

    @JsonProperty("crypto")
    @field:Valid
    @Schema(description = "User's cryptocurrency information")
    val crypto: Crypto? = null,

    @JsonProperty("role")
    @Schema(description = "User's role", example = "user")
    val role: String? = null
)

@Schema(description = "Data Transfer Object for updating user's age")
data class UpdateUserAgeDto(
    @field:Min(value = 0, message = "Age must be at least 0")
    @field:Max(value = 150, message = "Age must be at most 150")
    @Schema(description = "User's new age", example = "31")
    val age: Int
)
