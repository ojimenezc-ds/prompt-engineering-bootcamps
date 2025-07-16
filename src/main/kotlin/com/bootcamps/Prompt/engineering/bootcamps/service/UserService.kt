package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): UsersResponse {
        val users = userRepository.findAll().map { it.toUser() }
        return UsersResponse(users = users, total = users.size, skip = 0, limit = users.size)
    }

    fun getUserById(id: Int): User? {
        return userRepository.findById(id).map { it.toUser() }.orElse(null)
    }

    fun createUser(user: User): User {
        val userEntity = UserEntity(
            firstName = user.firstName,
            lastName = user.lastName,
            maidenName = user.maidenName,
            age = user.age,
            gender = user.gender,
            email = user.email,
            phone = user.phone,
            username = user.username,
            password = user.password,
            birthDate = user.birthDate,
            role = user.role
        )
        return userRepository.save(userEntity).toUser()
    }

    fun updateUserAge(id: Int, newAge: Int): User? {
        val existingUser = userRepository.findById(id).orElse(null) ?: return null
        val updatedUser = existingUser.copy(age = newAge)
        return userRepository.save(updatedUser).toUser()
    }

    fun deleteUser(id: Int): Boolean {
        if (!userRepository.existsById(id)) return false
        userRepository.deleteById(id)
        return true
    }

    private fun UserEntity.toUser(): User = User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        maidenName = maidenName,
        age = age,
        gender = gender,
        email = email,
        phone = phone,
        username = username,
        password = password,
        birthDate = birthDate,
        role = role,
        image = "",
        bloodGroup = "",
        height = 0.0,
        weight = 0.0,
        eyeColor = "",
        hair = Hair(color = "", type = ""),
        ip = "",
        address = Address(
            address = "",
            city = "",
            state = "",
            stateCode = "",
            postalCode = "",
            coordinates = Coordinates(lat = 0.0, lng = 0.0),
            country = ""
        ),
        macAddress = "",
        university = "",
        bank = Bank(
            cardExpire = "",
            cardNumber = "",
            cardType = "",
            currency = "",
            iban = ""
        ),
        company = Company(
            department = ""
        ),
        ein = "",
        ssn = "",
        userAgent = "",
        crypto = Crypto()
    )
}
