package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.dto.CreateUserRequest
import com.bootcamps.Prompt.engineering.bootcamps.dto.UpdateAgeRequest
import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.annotation.PostConstruct
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class UserService {
    private val users = mutableMapOf<Int, User>()
    private val idCounter = AtomicInteger(0)

    @PostConstruct
    fun init() {
        try {
            val resource = ClassPathResource("users.json")
            val json = resource.inputStream.bufferedReader().use { it.readText() }
            val userList = jacksonObjectMapper().readValue<Map<String, List<User>>>(json)['users'] ?: emptyList()
            
            userList.forEach { user ->
                users[user.id] = user
                if (user.id > idCounter.get()) {
                    idCounter.set(user.id)
                }
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to load users from JSON file", e)
        }
    }

    fun getAllUsers(): List<User> = users.values.toList()

    fun getUserById(id: Int): User? = users[id]

    fun createUser(request: CreateUserRequest): User {
        val id = idCounter.incrementAndGet()
        val user = User(
            id = id,
            firstName = request.firstName,
            lastName = request.lastName,
            age = request.age,
            gender = request.gender,
            email = request.email
        )
        users[id] = user
        return user
    }

    fun updateUserAge(id: Int, request: UpdateAgeRequest): User? {
        return users[id]?.apply {
            age = request.age
        }
    }

    fun deleteUser(id: Int): Boolean = users.remove(id) != null
}
