package com.bootcamps.Prompt.engineering.bootcamps.controller

import com.bootcamps.Prompt.engineering.bootcamps.dto.CreateUserRequest
import com.bootcamps.Prompt.engineering.bootcamps.dto.UpdateUserAgeRequest
import com.bootcamps.Prompt.engineering.bootcamps.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should get all users`() {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.users").isArray)
            .andExpect(jsonPath("$.total").isNumber)
    }

    @Test
    fun `should get user by id`() {
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.firstName").exists())
    }

    @Test
    fun `should return 404 for non-existent user`() {
        mockMvc.perform(get("/api/users/99999"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should create new user`() {
        val createUserRequest = CreateUserRequest(
            firstName = "John",
            lastName = "Doe",
            age = 30,
            gender = "male",
            email = "john.doe@example.com",
            phone = "+1-555-123-4567",
            username = "johndoe",
            password = "password123",
            birthDate = "1994-01-01"
        )

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.lastName").value("Doe"))
            .andExpect(jsonPath("$.age").value(30))
    }

    @Test
    fun `should update user age`() {
        val updateRequest = UpdateUserAgeRequest(age = 35)

        mockMvc.perform(patch("/api/users/1/age")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.age").value(35))
    }

    @Test
    fun `should return 404 when updating non-existent user age`() {
        val updateRequest = UpdateUserAgeRequest(age = 35)

        mockMvc.perform(patch("/api/users/99999/age")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should delete user`() {
        mockMvc.perform(delete("/api/users/2"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `should return 404 when deleting non-existent user`() {
        mockMvc.perform(delete("/api/users/99999"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return 400 for invalid create user request`() {
        val invalidRequest = CreateUserRequest(
            firstName = "", // Invalid - empty
            lastName = "Doe",
            age = -5, // Invalid - negative
            gender = "male",
            email = "invalid-email", // Invalid format
            phone = "+1-555-123-4567",
            username = "johndoe",
            password = "password123",
            birthDate = "1994-01-01"
        )

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest)
    }
}
