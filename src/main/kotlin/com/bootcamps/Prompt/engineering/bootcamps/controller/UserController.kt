package com.bootcamps.Prompt.engineering.bootcamps.controller

import com.bootcamps.Prompt.engineering.bootcamps.dto.CreateUserRequest
import com.bootcamps.Prompt.engineering.bootcamps.dto.UpdateAgeRequest
import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
class UserController(private val userService: UserService) {

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
    fun getAllUsers(): List<User> = userService.getAllUsers()

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by their ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User found"),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    fun getUserById(@PathVariable id: Int): ResponseEntity<User> {
        return userService.getUserById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "User created successfully"),
            ApiResponse(responseCode = "400", description = "Invalid input")
        ]
    )
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<User> {
        val user = userService.createUser(request)
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user.id)
            .toUri()
        return ResponseEntity.created(location).body(user)
    }

    @PatchMapping("/{id}/age")
    @Operation(summary = "Update user's age", description = "Updates the age of a specific user")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Age updated successfully"),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    fun updateUserAge(
        @PathVariable id: Int,
        @RequestBody request: UpdateAgeRequest
    ): ResponseEntity<User> {
        return userService.updateUserAge(id, request)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user", description = "Deletes a specific user by their ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "User deleted successfully"),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    fun deleteUser(@PathVariable id: Int): ResponseEntity<Void> {
        return if (userService.deleteUser(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
