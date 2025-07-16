package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.model.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class UserService {

    private val objectMapper = jacksonObjectMapper()
    private val userIdCounter = AtomicInteger()
    private var usersData: UsersResponse = loadUsersFromFile()

    private fun loadUsersFromFile(): UsersResponse {
        return try {
            val resource = ClassPathResource("users.json")
            val data = objectMapper.readValue(resource.inputStream, UsersResponse::class.java)
            val maxId = data.users.maxOfOrNull { it.id } ?: 0
            userIdCounter.set(maxId + 1)
            data
        } catch (e: Exception) {
            throw RuntimeException("Failed to load users from JSON file", e)
        }
    }

    fun getAllUsers(): UsersResponse = usersData

    fun getUserById(id: Int): User? = usersData.users.find { it.id == id }

    fun createUser(request: CreateUserRequest): User {
        val newUser = User(
            id = userIdCounter.getAndIncrement(),
            firstName = request.firstName,
            lastName = request.lastName,
            maidenName = request.maidenName ?: "",
            age = request.age,
            gender = request.gender,
            email = request.email,
            phone = request.phone,
            username = request.username,
            password = request.password,
            birthDate = request.birthDate,
            image = request.image ?: "https://dummyjson.com/icon/${request.username}/128",
            bloodGroup = request.bloodGroup ?: "O+",
            height = request.height ?: 170.0,
            weight = request.weight ?: 70.0,
            eyeColor = request.eyeColor ?: "Brown",
            hair = request.hair ?: Hair(color = "Brown", type = "Straight"),
            ip = request.ip ?: "127.0.0.1",
            address = request.address ?: createDefaultAddress(),
            macAddress = request.macAddress ?: "00:00:00:00:00:00",
            university = request.university ?: "",
            bank = request.bank ?: createDefaultBank(),
            company = request.company ?: createDefaultCompany(),
            ein = request.ein ?: "000-000",
            ssn = request.ssn ?: "000-00-0000",
            userAgent = request.userAgent ?: "Default User Agent",
            crypto = request.crypto ?: createDefaultCrypto(),
            role = request.role
        )

        usersData = usersData.copy(
            users = usersData.users + newUser,
            total = usersData.users.size + 1
        )
        return newUser
    }

    fun updateUserAge(id: Int, newAge: Int): User? {
        val userIndex = usersData.users.indexOfFirst { it.id == id }
        if (userIndex == -1) return null

        val updatedUser = usersData.users[userIndex].copy(age = newAge)
        val updatedUsers = usersData.users.toMutableList()
        updatedUsers[userIndex] = updatedUser

        usersData = usersData.copy(users = updatedUsers)
        return updatedUser
    }

    fun deleteUser(id: Int): Boolean {
        val filteredUsers = usersData.users.filter { it.id != id }
        
        return if (filteredUsers.size < usersData.users.size) {
            usersData = usersData.copy(
                users = filteredUsers,
                total = filteredUsers.size
            )
            true
        } else {
            false
        }
    }

    private fun createDefaultAddress() = Address(
        address = null, city = null, state = null, stateCode = null,
        postalCode = null, coordinates = null, country = "United States"
    )

    private fun createDefaultBank() = Bank(
        cardExpire = null, cardNumber = null, cardType = null,
        currency = null, iban = "DEFAULT_IBAN"
    )

    private fun createDefaultCompany() = Company(
        department = null, name = null, title = null, address = null
    )

    private fun createDefaultCrypto() = Crypto(
        coin = null, wallet = null, network = "Ethereum (ERC20)"
    )
}
