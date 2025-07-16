package com.bootcamps.Prompt.engineering.bootcamps.controller

import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.bootcamps.Prompt.engineering.bootcamps.model.CreateUserRequest
import com.bootcamps.Prompt.engineering.bootcamps.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

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

    @PostMapping
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user and adds it to the users.json file"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "User successfully created"),
        ApiResponse(responseCode = "400", description = "Invalid input data"),
        ApiResponse(responseCode = "409", description = "User with username or email already exists"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun createUser(
        @Valid @RequestBody createUserRequest: CreateUserRequest
    ): ResponseEntity<User> {
        return try {
            // Check if user with same username or email already exists
            val existingUsers = userService.getAllUsers()
            val usernameExists = existingUsers.users.any { it.username == createUserRequest.username }
            val emailExists = existingUsers.users.any { it.email == createUserRequest.email }
            
            if (usernameExists || emailExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build()
            }
            
            val newUser = userService.addUser(createUserRequest)
            ResponseEntity.status(HttpStatus.CREATED).body(newUser)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get user by ID",
        description = "Retrieves a specific user by their ID from the users.json file"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User found"),
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
}
