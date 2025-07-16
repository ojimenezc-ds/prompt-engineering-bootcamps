package com.bootcamps.Prompt.engineering.bootcamps.controller

import com.bootcamps.Prompt.engineering.bootcamps.model.*
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
        description = "Creates a new user with the provided information"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "User created successfully"),
        ApiResponse(responseCode = "400", description = "Invalid request data"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun createUser(@RequestBody createUserRequest: CreateUserRequest): ResponseEntity<User> {
        return try {
            // Create a full User object with default values for non-essential fields
            val newUser = User(
                id = 0, // Will be auto-generated
                firstName = createUserRequest.firstName,
                lastName = createUserRequest.lastName,
                maidenName = null,
                age = createUserRequest.age,
                gender = createUserRequest.gender,
                email = createUserRequest.email,
                phone = createUserRequest.phone,
                username = createUserRequest.username,
                password = "defaultpass", // You might want to handle this differently
                birthDate = "1990-01-01", // Default date
                image = "https://dummyjson.com/icon/default/128",
                bloodGroup = "O+",
                height = 170.0,
                weight = 70.0,
                eyeColor = "Brown",
                hair = Hair(color = "Brown", type = "Straight"),
                ip = "127.0.0.1",
                address = Address(
                    address = "Default Address",
                    city = "Default City",
                    state = "Default State",
                    stateCode = "DS",
                    postalCode = "00000",
                    coordinates = Coordinates(lat = 0.0, lng = 0.0),
                    country = "Default Country"
                ),
                macAddress = "00:00:00:00:00:00",
                university = "Default University",
                bank = Bank(
                    cardExpire = "12/25",
                    cardNumber = "0000000000000000",
                    cardType = "Visa",
                    currency = "USD",
                    iban = "DEFAULT IBAN"
                ),
                company = Company(
                    department = "IT",
                    name = "Default Company",
                    title = "Employee",
                    address = Address(
                        address = "Default Company Address",
                        city = "Default City",
                        state = "Default State",
                        stateCode = "DS",
                        postalCode = "00000",
                        coordinates = Coordinates(lat = 0.0, lng = 0.0),
                        country = "Default Country"
                    )
                ),
                ein = "000000000",
                ssn = "000000000",
                userAgent = "Default User Agent",
                crypto = Crypto(
                    coin = "Bitcoin",
                    wallet = "0x0000000000000000000000000000000000000000",
                    network = "Ethereum (ERC20)"
                ),
                role = "user"
            )

            val createdUser = userService.createUser(newUser)
            ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @PutMapping("/{id}/age")
    @Operation(
        summary = "Update user's age",
        description = "Updates the age of a specific user"
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Age updated successfully"),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(responseCode = "400", description = "Invalid request data"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun updateUserAge(
        @Parameter(description = "User ID", required = true)
        @PathVariable id: Int,
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
        description = "Deletes a specific user by their ID"
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
