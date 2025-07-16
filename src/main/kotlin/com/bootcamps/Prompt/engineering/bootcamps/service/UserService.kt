package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

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
}
