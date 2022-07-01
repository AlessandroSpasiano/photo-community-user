package it.alexs.photocommunityuser.repository

import it.alexs.photocommunityuser.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): Optional<User>
}