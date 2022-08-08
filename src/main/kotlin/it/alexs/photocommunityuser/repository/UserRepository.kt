package it.alexs.photocommunityuser.repository

import it.alexs.photocommunityuser.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): Optional<User>
}