package com.bootcamps.Prompt.engineering.bootcamps.controller

import com.bootcamps.Prompt.engineering.bootcamps.model.*
import com.bootcamps.Prompt.engineering.bootcamps.service.UserService
import io.swagger.v3.oas.annotations.Operation
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
    @Operation(summary = "Get all users", description = "Retrieves all users from the system")
    fun getAllUsers(): ResponseEntity<UsersResponse> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by their ID")
    fun getUserById(@PathVariable id: Int): ResponseEntity<User> {
        val user = userService.getUserById(id)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    fun createUser(@Valid @RequestBody request: CreateUserRequest): ResponseEntity<User> {
        val newUser = userService.createUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser)
    }

    @PatchMapping("/{id}/age")
    @Operation(summary = "Update user's age", description = "Updates the age of a specific user")
    fun updateUserAge(
        @PathVariable id: Int,
        @Valid @RequestBody request: UpdateUserAgeRequest
    ): ResponseEntity<User> {
        val updatedUser = userService.updateUserAge(id, request.age)
        return if (updatedUser != null) ResponseEntity.ok(updatedUser) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID", description = "Deletes a specific user by their ID")
    fun deleteUser(@PathVariable id: Int): ResponseEntity<Void> {
        val deleted = userService.deleteUser(id)
        return if (deleted) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
    }
}
