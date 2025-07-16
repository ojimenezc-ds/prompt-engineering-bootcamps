package com.bootcamps.Prompt.engineering.bootcamps.controller

import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.bootcamps.Prompt.engineering.bootcamps.dto.CreateUserRequest
import com.bootcamps.Prompt.engineering.bootcamps.dto.UpdateUserAgeRequest
import com.bootcamps.Prompt.engineering.bootcamps.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management API")
class UserController(private val userService: UserService) {

    @GetMapping
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

    @GetMapping("/{id}")
    @Operation(
        summary = "Get user by ID",
        description = "Retrieves a specific user by their ID"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun getUserById(
        @Parameter(description = "User ID", required = true)
        @PathVariable id: Int
    ): ResponseEntity<User> {
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

    @PostMapping
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user and adds it to the users.json file"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "User created successfully"),
        ApiResponse(responseCode = "400", description = "Invalid request body"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun createUser(
        @Parameter(description = "User data", required = true)
        @RequestBody createUserRequest: CreateUserRequest
    ): ResponseEntity<User> {
        return try {
            val newUser = userService.createUser(createUserRequest)
            ResponseEntity.status(HttpStatus.CREATED).body(newUser)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @PatchMapping("/{id}/age")
    @Operation(
        summary = "Update user's age",
        description = "Updates the age of a specific user by their ID"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User age updated successfully"),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(responseCode = "400", description = "Invalid request body"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun updateUserAge(
        @Parameter(description = "User ID", required = true)
        @PathVariable id: Int,
        @Parameter(description = "New age data", required = true)
        @RequestBody updateAgeRequest: UpdateUserAgeRequest
    ): ResponseEntity<User> {
        return try {
            val updatedUser = userService.updateUserAge(id, updateAgeRequest.age)
            if (updatedUser != null) {
                ResponseEntity.ok(updatedUser)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete user by ID",
        description = "Deletes a specific user by their ID from the users.json file"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "User deleted successfully"),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun deleteUser(
        @Parameter(description = "User ID", required = true)
        @PathVariable id: Int
    ): ResponseEntity<Void> {
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
