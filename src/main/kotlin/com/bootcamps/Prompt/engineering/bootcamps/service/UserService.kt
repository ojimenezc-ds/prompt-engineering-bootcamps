package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File
import java.util.concurrent.atomic.AtomicLong

@Service
class UserService(private val objectMapper: ObjectMapper) {
    
    // Thread-safe ID generation
    private val nextId = AtomicLong(1)
    
    init {
        // Initialize nextId based on existing users
        val users = getAllUsers()
        if (users.isNotEmpty()) {
            val maxId = users.maxOf { it.id }
            nextId.set(maxId + 1)
        }
    }

    fun getAllUsers(): List<User> {
        return try {
            val resource = ClassPathResource("users.json")
            val usersWrapper: Map<String, List<User>> = objectMapper.readValue(resource.inputStream)
            usersWrapper["users"] ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getUserById(id: Long): User? {
        return getAllUsers().find { it.id == id }
    }

    fun createUser(createUserDto: CreateUserDto): User {
        val users = getAllUsers().toMutableList()
        
        // Check for duplicate email or username
        if (users.any { it.email == createUserDto.email }) {
            throw IllegalArgumentException("User with email ${createUserDto.email} already exists")
        }
        if (users.any { it.username == createUserDto.username }) {
            throw IllegalArgumentException("User with username ${createUserDto.username} already exists")
        }

        val newUser = User(
            id = nextId.getAndIncrement(),
            firstName = createUserDto.firstName,
            lastName = createUserDto.lastName,
            maidenName = createUserDto.maidenName,
            age = createUserDto.age,
            gender = createUserDto.gender,
            email = createUserDto.email,
            phone = createUserDto.phone,
            username = createUserDto.username,
            password = createUserDto.password,
            birthDate = createUserDto.birthDate,
            image = createUserDto.image,
            bloodGroup = createUserDto.bloodGroup,
            height = createUserDto.height,
            weight = createUserDto.weight,
            eyeColor = createUserDto.eyeColor,
            hair = createUserDto.hair,
            ip = createUserDto.ip,
            address = createUserDto.address,
            macAddress = createUserDto.macAddress,
            university = createUserDto.university,
            bank = createUserDto.bank,
            company = createUserDto.company,
            ein = createUserDto.ein,
            ssn = createUserDto.ssn,
            userAgent = createUserDto.userAgent,
            crypto = createUserDto.crypto,
            role = createUserDto.role
        )

        users.add(newUser)
        saveUsers(users)
        return newUser
    }

    fun updateUserAge(id: Long, updateUserAgeDto: UpdateUserAgeDto): User? {
        val users = getAllUsers().toMutableList()
        val userIndex = users.indexOfFirst { it.id == id }
        
        if (userIndex == -1) return null
        
        val updatedUser = users[userIndex].copy(age = updateUserAgeDto.age)
        users[userIndex] = updatedUser
        saveUsers(users)
        return updatedUser
    }

    fun deleteUser(id: Long): Boolean {
        val users = getAllUsers().toMutableList()
        val removed = users.removeIf { it.id == id }
        
        if (removed) {
            saveUsers(users)
        }
        
        return removed
    }

    private fun saveUsers(users: List<User>) {
        try {
            val usersWrapper = mapOf("users" to users)
            val resourceFile = ClassPathResource("users.json").file
            objectMapper.writeValue(resourceFile, usersWrapper)
        } catch (e: Exception) {
            // If we can't write to the classpath resource, write to a local file
            val localFile = File("src/main/resources/users.json")
            val usersWrapper = mapOf("users" to users)
            objectMapper.writeValue(localFile, usersWrapper)
        }
    }
}
