package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.entity.UserEntity
import com.bootcamps.Prompt.engineering.bootcamps.model.User
import com.bootcamps.Prompt.engineering.bootcamps.model.UsersResponse
import com.bootcamps.Prompt.engineering.bootcamps.repository.UserRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import jakarta.annotation.PostConstruct

@Service
class DataMigrationService(
    private val userRepository: UserRepository
) : CommandLineRunner {

    private val objectMapper = jacksonObjectMapper()

    override fun run(vararg args: String?) {
        // Check if data already exists
        if (userRepository.count() == 0L) {
            migrateJsonDataToDatabase()
        }
    }

    private fun migrateJsonDataToDatabase() {
        try {
            println("Starting data migration from JSON to SQLite...")
            
            val resource = ClassPathResource("users.json")
            val inputStream = resource.inputStream
            val usersResponse = objectMapper.readValue(inputStream, UsersResponse::class.java)
            
            var migratedCount = 0
            usersResponse.users.forEach { user ->
                try {
                    val userEntity = mapUserToEntity(user)
                    userRepository.save(userEntity)
                    migratedCount++
                } catch (e: Exception) {
                    println("Error migrating user ${user.username}: ${e.message}")
                }
            }
            
            println("Successfully migrated $migratedCount users to SQLite database")
            
        } catch (e: Exception) {
            println("Error during data migration: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun mapUserToEntity(user: User): UserEntity {
        return UserEntity(
            id = 0L, // Let JPA auto-generate IDs
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
            image = user.image,
            bloodGroup = user.bloodGroup,
            height = user.height,
            weight = user.weight,
            eyeColor = user.eyeColor,
            hairColor = user.hair?.color,
            hairType = user.hair?.type,
            ip = user.ip,
            address = user.address?.address,
            city = user.address?.city,
            state = user.address?.state,
            stateCode = user.address?.stateCode,
            postalCode = user.address?.postalCode,
            latitude = user.address?.coordinates?.lat,
            longitude = user.address?.coordinates?.lng,
            country = user.address?.country,
            macAddress = user.macAddress,
            university = user.university,
            bankCardExpire = user.bank?.cardExpire,
            bankCardNumber = user.bank?.cardNumber,
            bankCardType = user.bank?.cardType,
            bankCurrency = user.bank?.currency,
            bankIban = user.bank?.iban,
            companyDepartment = user.company?.department,
            companyName = user.company?.name,
            companyTitle = user.company?.title,
            companyAddress = user.company?.address?.address,
            companyCity = user.company?.address?.city,
            companyState = user.company?.address?.state,
            companyStateCode = user.company?.address?.stateCode,
            companyPostalCode = user.company?.address?.postalCode,
            companyLatitude = user.company?.address?.coordinates?.lat,
            companyLongitude = user.company?.address?.coordinates?.lng,
            companyCountry = user.company?.address?.country,
            ein = user.ein,
            ssn = user.ssn,
            userAgent = user.userAgent,
            cryptoCoin = user.crypto?.coin,
            cryptoWallet = user.crypto?.wallet,
            cryptoNetwork = user.crypto?.network,
            role = user.role
        )
    }
}
