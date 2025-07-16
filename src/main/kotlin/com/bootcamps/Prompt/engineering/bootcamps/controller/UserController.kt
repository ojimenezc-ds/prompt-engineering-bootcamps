package com.bootcamps.Prompt.engineering.bootcamps.controller

import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.bootcamps.Prompt.engineering.bootcamps.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
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
        @Parameter(description = "ID of the user to retrieve")
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
        description = "Creates a new user with the provided details"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "User successfully created"),
        ApiResponse(responseCode = "400", description = "Invalid user data"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun createUser(
        @Parameter(description = "User details")
        @RequestBody user: User
    ): ResponseEntity<User> {
        return try {
            val createdUser = userService.createUser(user)
            ResponseEntity.status(201).body(createdUser)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @PatchMapping("/{id}/age")
    @Operation(
        summary = "Update user's age",
        description = "Updates the age of a specific user"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User age successfully updated"),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun updateUserAge(
        @Parameter(description = "ID of the user to update")
        @PathVariable id: Int,
        @Parameter(description = "New age value")
        @RequestBody newAge: Int
    ): ResponseEntity<User> {
        return try {
            val updatedUser = userService.updateUserAge(id, newAge)
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
        summary = "Delete user",
        description = "Deletes a user by their ID"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "User successfully deleted"),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun deleteUser(
        @Parameter(description = "ID of the user to delete")
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
