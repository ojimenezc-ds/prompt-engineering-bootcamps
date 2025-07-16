package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.entity.UserEntity
import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.bootcamps.Prompt.engineering.bootcamps.repository.UserRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class DataMigrationService(private val userRepository: UserRepository) {
    
    private val objectMapper = jacksonObjectMapper()
    
    @EventListener(ApplicationReadyEvent::class)
    fun migrateData() {
        if (userRepository.count() == 0L) {
            val resource = ClassPathResource("users.json")
            val usersResponse = objectMapper.readValue(resource.inputStream, UsersResponse::class.java)
            
            usersResponse.users.forEach { user ->
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
                userRepository.save(userEntity)
            }
        }
    }
}
