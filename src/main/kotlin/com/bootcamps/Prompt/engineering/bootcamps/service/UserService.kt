package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.entity.UserEntity
import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.bootcamps.Prompt.engineering.bootcamps.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapperService: UserMapperService
) {

    fun getUserCount(): Long {
        return try {
            userRepository.count()
        } catch (e: Exception) {
            throw RuntimeException("Failed to get user count: ${e.message}", e)
        }
    }

    fun getAllUsers(): UsersResponse {
        return try {
            val allUsers = userRepository.findAll()
            val users = allUsers.map { entity ->
                try {
                    userMapperService.mapEntityToUser(entity)
                } catch (e: Exception) {
                    throw RuntimeException("Failed to map entity to user: ${e.message}", e)
                }
            }
            UsersResponse(
                users = users,
                total = users.size,
                skip = 0,
                limit = 30
            )
        } catch (e: Exception) {
            throw RuntimeException("Failed to retrieve users from database: ${e.message}", e)
        }
    }

    fun getUserById(id: Int): User? {
        return try {
            val userEntity = userRepository.findById(id.toLong())
            if (userEntity.isPresent) {
                try {
                    userMapperService.mapEntityToUser(userEntity.get())
                } catch (e: Exception) {
                    throw RuntimeException("Failed to map entity to user for ID $id: ${e.message}", e)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to retrieve user with ID $id from database: ${e.message}", e)
        }
    }

    fun updateUserAge(id: Int, newAge: Int): User? {
        return try {
            val userEntity = userRepository.findById(id.toLong())
            if (userEntity.isPresent) {
                val updatedEntity = userEntity.get().copy(age = newAge)
                val savedEntity = userRepository.save(updatedEntity)
                userMapperService.mapEntityToUser(savedEntity)
            } else {
                null
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to update age for user with ID $id", e)
        }
    }

    fun deleteUser(id: Int): Boolean {
        return try {
            val userEntity = userRepository.findById(id.toLong())
            if (userEntity.isPresent) {
                userRepository.deleteById(id.toLong())
                true
            } else {
                false
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to delete user with ID $id", e)
        }
    }

    fun createUser(createUserRequest: CreateUserRequest): User {
        return try {
            // Check if username or email already exists
            if (userRepository.existsByUsername(createUserRequest.username)) {
                throw IllegalArgumentException("Username already exists")
            }
            if (userRepository.existsByEmail(createUserRequest.email)) {
                throw IllegalArgumentException("Email already exists")
            }
            
            val newUserEntity = userMapperService.mapCreateRequestToEntity(createUserRequest).copy(
                ip = "192.168.1.${Random.nextInt(1, 255)}",
                macAddress = generateMacAddress(),
                university = createUserRequest.university ?: "Unknown University",
                bankCurrency = "USD",
                bankIban = "US00000000000000000000",
                ein = generateEIN(),
                ssn = generateSSN(),
                userAgent = "Mozilla/5.0 (API Created User)",
                cryptoNetwork = "Ethereum",
                role = createUserRequest.role ?: "user"
            )
            
            val savedEntity = userRepository.save(newUserEntity)
            userMapperService.mapEntityToUser(savedEntity)
            
        } catch (e: Exception) {
            throw RuntimeException("Failed to create user", e)
        }
    }

    private fun generateMacAddress(): String {
        return (1..6).map { 
            Random.nextInt(0, 256).toString(16).padStart(2, '0') 
        }.joinToString(":")
    }

    private fun generateEIN(): String {
        return "${Random.nextInt(10, 99)}-${Random.nextInt(1000000, 9999999)}"
    }

    private fun generateSSN(): String {
        return "${Random.nextInt(100, 999)}-${Random.nextInt(10, 99)}-${Random.nextInt(1000, 9999)}"
    }
}

data class CreateUserRequest(
    val firstName: String,
    val lastName: String,
    val maidenName: String? = null,
    val age: Int,
    val gender: String,
    val email: String,
    val phone: String? = null,
    val username: String,
    val password: String,
    val birthDate: String? = null,
    val image: String? = null,
    val bloodGroup: String? = null,
    val height: Double? = null,
    val weight: Double? = null,
    val eyeColor: String? = null,
    val hairColor: String? = null,
    val hairType: String? = null,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val stateCode: String? = null,
    val postalCode: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val country: String? = null,
    val university: String? = null,
    val department: String? = null,
    val company: String? = null,
    val jobTitle: String? = null,
    val role: String? = null
)

data class UpdateUserAgeRequest(
    val age: Int
)
