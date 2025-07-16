package com.bootcamps.Prompt.engineering.bootcamps.controller

import com.bootcamps.Prompt.engineering.bootcamps.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/test")
class TestController(private val userRepository: UserRepository) {

    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<Map<String, Any>> {
        return try {
            val count = userRepository.count()
            val response: Map<String, Any> = mapOf(
                "status" to "OK",
                "timestamp" to System.currentTimeMillis(),
                "userCount" to count
            )
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            val errorResponse: Map<String, Any> = mapOf("error" to (e.message ?: "Unknown error"))
            ResponseEntity.internalServerError().body(errorResponse)
        }
    }

    @GetMapping("/simple")
    fun getSimpleUsers(): ResponseEntity<List<Map<String, Any>>> {
        return try {
            val entities = userRepository.findAll()
            val simplified: List<Map<String, Any>> = entities.take(3).map { entity ->
                mapOf(
                    "id" to entity.id,
                    "name" to "${entity.firstName} ${entity.lastName}",
                    "email" to entity.email
                )
            }
            ResponseEntity.ok(simplified)
        } catch (e: Exception) {
            val errorResponse: List<Map<String, Any>> = listOf(mapOf("error" to (e.message ?: "Unknown error")))
            ResponseEntity.internalServerError().body(errorResponse)
        }
    }

    @GetMapping("/raw")
    fun getRawUsers(): ResponseEntity<List<Map<String, Any>>> {
        return try {
            val entities = userRepository.findAll()
            val simplified: List<Map<String, Any>> = entities.take(5).map { entity ->
                mapOf(
                    "id" to entity.id,
                    "firstName" to entity.firstName,
                    "lastName" to entity.lastName,
                    "email" to entity.email,
                    "hairColor" to (entity.hairColor ?: "Unknown"),
                    "hairType" to (entity.hairType ?: "Unknown")
                )
            }
            ResponseEntity.ok(simplified)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(emptyList())
        }
    }
}
