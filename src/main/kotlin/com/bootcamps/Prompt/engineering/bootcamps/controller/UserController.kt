package com.bootcamps.Prompt.engineering.bootcamps.controller

import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.bootcamps.Prompt.engineering.bootcamps.service.UserService
import com.bootcamps.Prompt.engineering.bootcamps.service.CreateUserRequest
import com.bootcamps.Prompt.engineering.bootcamps.service.UpdateUserAgeRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Tag(name = "Users", description = "User management API")
class UserController(private val userService: UserService) {

    @GetMapping("/health")
    @Operation(
        summary = "Health check",
        description = "Simple health check endpoint"
    )
    fun healthCheck(): ResponseEntity<Map<String, Any>> {
        return try {
            val count = userService.getUserCount()
            val response = mapOf(
                "status" to "OK",
                "timestamp" to System.currentTimeMillis(),
                "userCount" to count
            )
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping("/users")
    @Operation(
        summary = "Get all users",
        description = "Retrieves all users from the users.json file"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun getAllUsers(): ResponseEntity<UsersResponse> {
        return try {
            val users = userService.getAllUsers()
            ResponseEntity.ok(users)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping("/users/{id}")
    @Operation(
        summary = "Get user by ID",
        description = "Retrieves a specific user by their ID"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun getUserById(@Parameter(description = "User ID") @PathVariable id: Int): ResponseEntity<User> {
        return try {
            val user = userService.getUserById(id)
            if (user != null) {
                ResponseEntity.ok(user)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @PostMapping("/users")
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user and saves it to the users.json file"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "User created successfully"),
        ApiResponse(responseCode = "400", description = "Invalid input data"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun createUser(@RequestBody createUserRequest: CreateUserRequest): ResponseEntity<User> {
        return try {
            val newUser = userService.createUser(createUserRequest)
            ResponseEntity.status(HttpStatus.CREATED).body(newUser)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @PatchMapping("/users/{id}/age")
    @Operation(
        summary = "Update user's age",
        description = "Updates the age of a specific user by their ID"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User age updated successfully"),
        ApiResponse(responseCode = "400", description = "Invalid input data"),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun updateUserAge(
        @Parameter(description = "User ID") @PathVariable id: Int,
        @RequestBody updateUserAgeRequest: UpdateUserAgeRequest
    ): ResponseEntity<User> {
        return try {
            // Validate age
            if (updateUserAgeRequest.age < 0 || updateUserAgeRequest.age > 150) {
                return ResponseEntity.badRequest().build()
            }
            
            val updatedUser = userService.updateUserAge(id, updateUserAgeRequest.age)
            if (updatedUser != null) {
                ResponseEntity.ok(updatedUser)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @DeleteMapping("/users/{id}")
    @Operation(
        summary = "Delete user by ID",
        description = "Deletes a specific user by their ID"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "User deleted successfully"),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun deleteUser(@Parameter(description = "User ID") @PathVariable id: Int): ResponseEntity<Void> {
        return try {
            val deleted = userService.deleteUser(id)
            if (deleted) {
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }
}
