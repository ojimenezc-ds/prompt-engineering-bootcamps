package com.bootcamps.Prompt.engineering.bootcamps.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hello")
@Tag(name = "Hello World", description = "Simple Hello World API")
class HelloWorldController {

    @GetMapping
    @Operation(summary = "Get Hello World message", description = "Returns a simple Hello World greeting")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful response")
    ])
    fun hello(): String {
        return "Hello World!"
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get personalized greeting", description = "Returns a personalized greeting with the provided name")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful response"),
        ApiResponse(responseCode = "400", description = "Invalid name provided")
    ])
    fun helloWithName(@PathVariable name: String): String {
        return "Hello, $name!"
    }
}
