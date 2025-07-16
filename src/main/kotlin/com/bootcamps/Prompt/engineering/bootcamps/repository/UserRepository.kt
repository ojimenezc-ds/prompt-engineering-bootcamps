package com.bootcamps.Prompt.engineering.bootcamps.repository

import com.bootcamps.Prompt.engineering.bootcamps.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}
