package com.bootcamps.Prompt.engineering.bootcamps.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    val firstName: String = "",
    
    @Column(nullable = false)
    val lastName: String = "",
    
    @Column
    val maidenName: String? = null,
    
    @Column(nullable = false)
    val age: Int = 0,
    
    @Column(nullable = false)
    val gender: String = "",
    
    @Column(nullable = false, unique = true)
    val email: String = "",
    
    @Column
    val phone: String? = null,
    
    @Column(nullable = false, unique = true)
    val username: String = "",
    
    @Column(nullable = false)
    val password: String = "",
    
    @Column
    val birthDate: String? = null,
    
    @Column
    val image: String? = null,
    
    @Column
    val bloodGroup: String? = null,
    
    @Column
    val height: Double? = null,
    
    @Column
    val weight: Double? = null,
    
    @Column
    val eyeColor: String? = null,
    
    @Column
    val hairColor: String? = null,
    
    @Column
    val hairType: String? = null,
    
    @Column
    val ip: String? = null,
    
    @Column
    val address: String? = null,
    
    @Column
    val city: String? = null,
    
    @Column
    val state: String? = null,
    
    @Column
    val stateCode: String? = null,
    
    @Column
    val postalCode: String? = null,
    
    @Column
    val latitude: Double? = null,
    
    @Column
    val longitude: Double? = null,
    
    @Column
    val country: String? = null,
    
    @Column
    val macAddress: String? = null,
    
    @Column
    val university: String? = null,
    
    @Column
    val bankCardExpire: String? = null,
    
    @Column
    val bankCardNumber: String? = null,
    
    @Column
    val bankCardType: String? = null,
    
    @Column
    val bankCurrency: String? = null,
    
    @Column
    val bankIban: String? = null,
    
    @Column
    val companyDepartment: String? = null,
    
    @Column
    val companyName: String? = null,
    
    @Column
    val companyTitle: String? = null,
    
    @Column
    val companyAddress: String? = null,
    
    @Column
    val companyCity: String? = null,
    
    @Column
    val companyState: String? = null,
    
    @Column
    val companyStateCode: String? = null,
    
    @Column
    val companyPostalCode: String? = null,
    
    @Column
    val companyLatitude: Double? = null,
    
    @Column
    val companyLongitude: Double? = null,
    
    @Column
    val companyCountry: String? = null,
    
    @Column
    val ein: String? = null,
    
    @Column
    val ssn: String? = null,
    
    @Column
    val userAgent: String? = null,
    
    @Column
    val cryptoCoin: String? = null,
    
    @Column
    val cryptoWallet: String? = null,
    
    @Column
    val cryptoNetwork: String? = null,
    
    @Column
    val role: String? = null
)
