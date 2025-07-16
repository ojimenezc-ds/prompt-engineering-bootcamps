package com.bootcamps.Prompt.engineering.bootcamps.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    
    @Column(nullable = false)
    val firstName: String,
    
    @Column(nullable = false)
    val lastName: String,
    
    @Column
    val maidenName: String?,
    
    @Column(nullable = false)
    val age: Int,
    
    @Column(nullable = false)
    val gender: String,
    
    @Column(nullable = false, unique = true)
    val email: String,
    
    @Column(nullable = false)
    val phone: String,
    
    @Column(nullable = false, unique = true)
    val username: String,
    
    @Column(nullable = false)
    val password: String,
    
    @Column(nullable = false)
    val birthDate: String,
    
    @Column(nullable = false)
    val role: String
)
