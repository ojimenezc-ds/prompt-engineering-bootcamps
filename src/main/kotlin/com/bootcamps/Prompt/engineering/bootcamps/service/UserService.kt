package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.bootcamps.Prompt.engineering.bootcamps.dto.CreateUserRequest
import com.bootcamps.Prompt.engineering.bootcamps.dto.UpdateUserAgeRequest
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
    private val usersFilePath = "src/main/resources/users.json"

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

    fun createUser(createUserRequest: CreateUserRequest): User {
        val usersResponse = getAllUsers()
        val newId = (usersResponse.users.maxByOrNull { it.id }?.id ?: 0) + 1
        
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
            image = createUserRequest.image,
            bloodGroup = createUserRequest.bloodGroup,
            height = createUserRequest.height,
            weight = createUserRequest.weight,
            eyeColor = createUserRequest.eyeColor,
            hair = createUserRequest.hair,
            ip = createUserRequest.ip,
            address = createUserRequest.address,
            macAddress = createUserRequest.macAddress,
            university = createUserRequest.university,
            bank = createUserRequest.bank,
            company = createUserRequest.company,
            ein = createUserRequest.ein,
            ssn = createUserRequest.ssn,
            userAgent = createUserRequest.userAgent,
            crypto = createUserRequest.crypto,
            role = createUserRequest.role
        )

        val updatedUsers = usersResponse.users.toMutableList()
        updatedUsers.add(newUser)
        
        val updatedUsersResponse = UsersResponse(
            users = updatedUsers,
            total = updatedUsers.size,
            skip = 0,
            limit = updatedUsers.size
        )
        
        saveUsersToFile(updatedUsersResponse)
        return newUser
    }

    fun updateUserAge(id: Int, updateRequest: UpdateUserAgeRequest): User? {
        val usersResponse = getAllUsers()
        val userIndex = usersResponse.users.indexOfFirst { it.id == id }
        
        if (userIndex == -1) {
            return null
        }
        
        val updatedUsers = usersResponse.users.toMutableList()
        val existingUser = updatedUsers[userIndex]
        val updatedUser = existingUser.copy(age = updateRequest.age)
        updatedUsers[userIndex] = updatedUser
        
        val updatedUsersResponse = UsersResponse(
            users = updatedUsers,
            total = updatedUsers.size,
            skip = 0,
            limit = updatedUsers.size
        )
        
        saveUsersToFile(updatedUsersResponse)
        return updatedUser
    }

    fun deleteUser(id: Int): Boolean {
        val usersResponse = getAllUsers()
        val userExists = usersResponse.users.any { it.id == id }
        
        if (!userExists) {
            return false
        }
        
        val updatedUsers = usersResponse.users.filter { it.id != id }
        val updatedUsersResponse = UsersResponse(
            users = updatedUsers,
            total = updatedUsers.size,
            skip = 0,
            limit = updatedUsers.size
        )
        
        saveUsersToFile(updatedUsersResponse)
        return true
    }

    private fun saveUsersToFile(usersResponse: UsersResponse) {
        try {
            val file = File(usersFilePath)
            FileWriter(file).use { writer ->
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, usersResponse)
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to save users to JSON file", e)
        }
    }
}
