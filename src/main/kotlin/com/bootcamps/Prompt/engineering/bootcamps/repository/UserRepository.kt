package com.bootcamps.Prompt.engineering.bootcamps.repository

import com.bootcamps.Prompt.engineering.bootcamps.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Int>
