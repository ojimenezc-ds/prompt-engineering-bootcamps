package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.bootcamps.Prompt.engineering.bootcamps.model.CreateUserRequest
import com.bootcamps.Prompt.engineering.bootcamps.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File
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

    fun getUserById(id: Int): User? {
        return try {
            val users = getAllUsers()
            users.users.find { it.id == id }
        } catch (e: Exception) {
            throw RuntimeException("Failed to get user by ID", e)
        }
    }

    fun addUser(createUserRequest: CreateUserRequest): User {
        return try {
            val currentUsers = getAllUsers()
            val nextId = (currentUsers.users.maxOfOrNull { it.id } ?: 0) + 1
            
            val newUser = User(
                id = nextId,
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
                image = createUserRequest.image ?: "https://dummyjson.com/icon/${createUserRequest.username}/128",
                bloodGroup = createUserRequest.bloodGroup ?: "Unknown",
                height = createUserRequest.height ?: 0.0,
                weight = createUserRequest.weight ?: 0.0,
                eyeColor = createUserRequest.eyeColor ?: "Unknown",
                hair = createUserRequest.hair ?: Hair(color = null, type = "Unknown"),
                ip = createUserRequest.ip ?: "0.0.0.0",
                address = createUserRequest.address ?: Address(
                    address = null,
                    city = null,
                    state = null,
                    stateCode = null,
                    postalCode = null,
                    coordinates = null,
                    country = "Unknown"
                ),
                macAddress = createUserRequest.macAddress ?: "00:00:00:00:00:00",
                university = createUserRequest.university ?: "Unknown",
                bank = createUserRequest.bank ?: Bank(
                    cardExpire = null,
                    cardNumber = null,
                    cardType = null,
                    currency = null,
                    iban = "Unknown"
                ),
                company = createUserRequest.company ?: Company(
                    department = null,
                    name = null,
                    title = null,
                    address = null
                ),
                ein = createUserRequest.ein ?: "000-000",
                ssn = createUserRequest.ssn ?: "000-000-000",
                userAgent = createUserRequest.userAgent ?: "Unknown",
                crypto = createUserRequest.crypto ?: Crypto(
                    coin = null,
                    wallet = null,
                    network = "Unknown"
                ),
                role = createUserRequest.role
            )

            val updatedUsers = currentUsers.copy(
                users = currentUsers.users + newUser,
                total = currentUsers.total + 1
            )

            // Save to file
            saveUsersToFile(updatedUsers)
            
            newUser
        } catch (e: Exception) {
            throw RuntimeException("Failed to add user", e)
        }
    }

    private fun saveUsersToFile(usersResponse: UsersResponse) {
        try {
            val classPath = this::class.java.classLoader.getResource("users.json")
            if (classPath != null) {
                val file = File(classPath.toURI())
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, usersResponse)
            } else {
                // Fallback: write to project resources directory
                val projectPath = System.getProperty("user.dir")
                val resourcesPath = Paths.get(projectPath, "src", "main", "resources", "users.json")
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(resourcesPath.toFile(), usersResponse)
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to save users to JSON file", e)
        }
    }
}
