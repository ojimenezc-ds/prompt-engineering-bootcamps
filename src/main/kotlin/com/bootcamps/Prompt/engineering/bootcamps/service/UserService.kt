package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.dto.CreateUserRequest
import com.bootcamps.Prompt.engineering.bootcamps.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths

@Service
class UserService {

    private val objectMapper = jacksonObjectMapper()

    fun getAllUsers(): UsersResponse {
        return try {
            val resource = ClassPathResource("users.json")
            val inputStream = resource.inputStream
            objectMapper.readValue(inputStream, UsersResponse::class.java)
        } catch (e: Exception) {
            throw RuntimeException("Failed to load users from JSON file", e)
        }
    }

    fun createUser(createUserRequest: CreateUserRequest): User {
        return try {
            val currentUsers = getAllUsers()
            val newId = (currentUsers.users.maxOfOrNull { it.id } ?: 0) + 1

            val newUser = User(
                id = newId,
                firstName = createUserRequest.firstName,
                lastName = createUserRequest.lastName,
                maidenName = createUserRequest.maidenName,
                age = createUserRequest.age,
                gender = createUserRequest.gender,
                email = createUserRequest.email,
                phone = createUserRequest.phone,
                username = createUserRequest.username,
                password = createUserRequest.password,
                birthDate = createUserRequest.birthDate,
                image = createUserRequest.image ?: "https://dummyjson.com/icon/default/128",
                bloodGroup = createUserRequest.bloodGroup ?: "Unknown",
                height = createUserRequest.height ?: 0.0,
                weight = createUserRequest.weight ?: 0.0,
                eyeColor = createUserRequest.eyeColor ?: "Unknown",
                hair = Hair(color = "Unknown", type = "Unknown"),
                ip = "0.0.0.0",
                address = Address(
                    address = null,
                    city = null,
                    state = null,
                    stateCode = null,
                    postalCode = null,
                    coordinates = null,
                    country = "Unknown"
                ),
                macAddress = "00:00:00:00:00:00",
                university = createUserRequest.university ?: "Unknown",
                bank = Bank(
                    cardExpire = null,
                    cardNumber = null,
                    cardType = null,
                    currency = null,
                    iban = ""
                ),
                company = Company(
                    department = null,
                    name = null,
                    title = null,
                    address = null
                ),
                ein = "",
                ssn = "",
                userAgent = "",
                crypto = Crypto(
                    coin = null,
                    wallet = null,
                    network = ""
                ),
                role = createUserRequest.role
            )

            val updatedUsers = currentUsers.copy(
                users = currentUsers.users + newUser,
                total = currentUsers.total + 1
            )

            // Write back to JSON file
            val resource = ClassPathResource("users.json")
            val file = File(resource.uri)
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, updatedUsers)

            newUser
        } catch (e: Exception) {
            throw RuntimeException("Failed to create user", e)
        }
    }
}
