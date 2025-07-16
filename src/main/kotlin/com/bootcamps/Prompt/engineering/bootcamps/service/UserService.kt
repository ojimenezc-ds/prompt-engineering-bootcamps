package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Service
class UserService {

    private val objectMapper = jacksonObjectMapper()
    private val jsonFilePath = "src/main/resources/users.json"

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
        val usersResponse = getAllUsers()
        return usersResponse.users.find { it.id == id }
    }

    fun createUser(user: User): User {
        val usersResponse = getAllUsers()
        val newId = (usersResponse.users.maxOfOrNull { it.id } ?: 0) + 1
        val newUser = user.copy(id = newId)

        val updatedUsers = usersResponse.users + newUser
        val updatedResponse = usersResponse.copy(
            users = updatedUsers,
            total = updatedUsers.size
        )

        saveUsersToFile(updatedResponse)
        return newUser
    }

    fun updateUserAge(id: Int, newAge: Int): User? {
        val usersResponse = getAllUsers()
        val userIndex = usersResponse.users.indexOfFirst { it.id == id }

        if (userIndex == -1) return null

        val updatedUser = usersResponse.users[userIndex].copy(age = newAge)
        val updatedUsers = usersResponse.users.toMutableList()
        updatedUsers[userIndex] = updatedUser

        val updatedResponse = usersResponse.copy(users = updatedUsers)
        saveUsersToFile(updatedResponse)

        return updatedUser
    }

    fun deleteUser(id: Int): Boolean {
        val usersResponse = getAllUsers()
        val userExists = usersResponse.users.any { it.id == id }

        if (!userExists) return false

        val updatedUsers = usersResponse.users.filter { it.id != id }
        val updatedResponse = usersResponse.copy(
            users = updatedUsers,
            total = updatedUsers.size
        )

        saveUsersToFile(updatedResponse)
        return true
    }

    private fun saveUsersToFile(usersResponse: UsersResponse) {
        try {
            val jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(usersResponse)
            Files.write(Paths.get(jsonFilePath), jsonString.toByteArray())
        } catch (e: Exception) {
            throw RuntimeException("Failed to save users to JSON file", e)
        }
    }
}
