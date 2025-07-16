package com.bootcamps.Prompt.engineering.bootcamps.service

import com.bootcamps.Prompt.engineering.bootcamps.entity.UserEntity
import com.bootcamps.Prompt.engineering.bootcamps.model.*
import org.springframework.stereotype.Service

@Service
class UserMapperService {

    fun mapEntityToUser(entity: UserEntity): User {
        return User(
            id = entity.id.toInt(),
            firstName = entity.firstName,
            lastName = entity.lastName,
            maidenName = entity.maidenName,
            age = entity.age,
            gender = entity.gender,
            email = entity.email,
            phone = entity.phone ?: "+1-000-000-0000",
            username = entity.username,
            password = entity.password,
            birthDate = entity.birthDate ?: "1990-01-01",
            image = entity.image ?: "https://dummyjson.com/icon/default/128",
            bloodGroup = entity.bloodGroup ?: "O+",
            height = entity.height ?: 170.0,
            weight = entity.weight ?: 70.0,
            eyeColor = entity.eyeColor ?: "Brown",
            hair = Hair(
                color = entity.hairColor ?: "Brown",
                type = entity.hairType ?: "Straight"
            ),
            ip = entity.ip ?: "192.168.1.1",
            address = Address(
                address = entity.address,
                city = entity.city,
                state = entity.state,
                stateCode = entity.stateCode,
                postalCode = entity.postalCode,
                coordinates = if (entity.latitude != null && entity.longitude != null) {
                    Coordinates(entity.latitude, entity.longitude)
                } else null,
                country = entity.country ?: "US"
            ),
            macAddress = entity.macAddress ?: "00:00:00:00:00:00",
            university = entity.university ?: "Unknown University",
            bank = Bank(
                cardExpire = entity.bankCardExpire,
                cardNumber = entity.bankCardNumber,
                cardType = entity.bankCardType,
                currency = entity.bankCurrency ?: "USD",
                iban = entity.bankIban ?: "US00000000000000000000"
            ),
            company = Company(
                department = entity.companyDepartment,
                name = entity.companyName,
                title = entity.companyTitle,
                address = if (entity.companyAddress != null) {
                    Address(
                        address = entity.companyAddress,
                        city = entity.companyCity,
                        state = entity.companyState,
                        stateCode = entity.companyStateCode,
                        postalCode = entity.companyPostalCode,
                        coordinates = if (entity.companyLatitude != null && entity.companyLongitude != null) {
                            Coordinates(entity.companyLatitude, entity.companyLongitude)
                        } else null,
                        country = entity.companyCountry ?: "US"
                    )
                } else null
            ),
            ein = entity.ein ?: "00-0000000",
            ssn = entity.ssn ?: "000-00-0000",
            userAgent = entity.userAgent ?: "Mozilla/5.0 (API User)",
            crypto = Crypto(
                coin = entity.cryptoCoin,
                wallet = entity.cryptoWallet,
                network = entity.cryptoNetwork ?: "Ethereum"
            ),
            role = entity.role ?: "user"
        )
    }

    fun mapCreateRequestToEntity(request: CreateUserRequest): UserEntity {
        return UserEntity(
            firstName = request.firstName,
            lastName = request.lastName,
            maidenName = request.maidenName,
            age = request.age,
            gender = request.gender,
            email = request.email,
            phone = request.phone,
            username = request.username,
            password = request.password,
            birthDate = request.birthDate,
            image = request.image,
            bloodGroup = request.bloodGroup,
            height = request.height,
            weight = request.weight,
            eyeColor = request.eyeColor,
            hairColor = request.hairColor,
            hairType = request.hairType,
            address = request.address,
            city = request.city,
            state = request.state,
            stateCode = request.stateCode,
            postalCode = request.postalCode,
            latitude = request.latitude,
            longitude = request.longitude,
            country = request.country,
            university = request.university,
            companyDepartment = request.department,
            companyName = request.company,
            companyTitle = request.jobTitle,
            role = request.role
        )
    }
}
