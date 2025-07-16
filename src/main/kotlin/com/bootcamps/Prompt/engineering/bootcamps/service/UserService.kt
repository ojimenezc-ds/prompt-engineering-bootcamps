package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File

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
        val newId = (usersResponse.users.maxOfOrNull { it.id } ?: 0) + 1
        
        val newUser = User(
            id = newId,
            firstName = createUserRequest.firstName,
            lastName = createUserRequest.lastName,
            maidenName = null,
            age = createUserRequest.age,
            gender = createUserRequest.gender,
            email = createUserRequest.email,
            phone = createUserRequest.phone,
            username = createUserRequest.username,
            password = "defaultpass",
            birthDate = "2000-01-01",
            image = "https://dummyjson.com/icon/default/128",
            bloodGroup = "O+",
            height = 170.0,
            weight = 70.0,
            eyeColor = "Brown",
            hair = Hair(color = "Brown", type = "Straight"),
            ip = "127.0.0.1",
            address = Address(
                address = "123 Default Street",
                city = "Default City",
                state = "Default State",
                stateCode = "DS",
                postalCode = "12345",
                coordinates = Coordinates(lat = 0.0, lng = 0.0),
                country = "United States"
            ),
            macAddress = "00:00:00:00:00:00",
            university = "Default University",
            bank = Bank(
                cardExpire = "12/99",
                cardNumber = "0000000000000000",
                cardType = "Default",
                currency = "USD",
                iban = "DEFAULT0000000000000000"
            ),
            company = Company(
                department = "Default Department",
                name = "Default Company",
                title = "Default Title",
                address = Address(
                    address = "123 Company Street",
                    city = "Company City",
                    state = "Company State",
                    stateCode = "CS",
                    postalCode = "54321",
                    coordinates = Coordinates(lat = 0.0, lng = 0.0),
                    country = "United States"
                )
            ),
            ein = "00-0000000",
            ssn = "000-00-0000",
            userAgent = "Default User Agent",
            crypto = Crypto(
                coin = "Bitcoin",
                wallet = "default-wallet",
                network = "Bitcoin"
            ),
            role = "user"
        )
        
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
            val file = File(usersFilePath)
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, usersResponse)
        } catch (e: Exception) {
            throw RuntimeException("Failed to save users to JSON file", e)
        }
    }
}
