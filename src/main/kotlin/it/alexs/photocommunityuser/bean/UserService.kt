package it.alexs.photocommunityuser.bean

import it.alexs.photocommunityuser.dtos.UserCreateDto
import it.alexs.photocommunityuser.entities.User
import it.alexs.photocommunityuser.repository.UserRepository
import it.alexs.photocommunityuser.utils.assertOrConflict
import it.alexs.photocommunityuser.utils.assertOrNotFound
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.OffsetDateTime
import javax.transaction.Transactional

@Service
class UserService(
    private val repository: UserRepository
) {

    fun getAll(pageable: Pageable): Page<User> {
        return repository.findAll(pageable)
    }

    fun getByID(id: Long): User {
        val maybeUser = repository.findById(id)

        assertOrNotFound(maybeUser.isPresent, "No user found for ID ${id}")

        return maybeUser.get()
    }

    fun getByEmail(email: String): User {
        val maybeUser = repository.findByEmail(email)

        assertOrNotFound(maybeUser.isEmpty, "No user found for ${email}")

        return maybeUser.get()
    }

    @Transactional
    fun createUser(userCreateDto: UserCreateDto): User {
        val existsByEmail = repository.existsByEmail(userCreateDto.email)

        assertOrConflict(!existsByEmail, "User with email '${userCreateDto.email}' already exists")

        val user = User(
            id = 0,
            firstName = userCreateDto.firstName,
            lastName = userCreateDto.lastName,
            email = userCreateDto.email,
            password = userCreateDto.password,
            birth = LocalDate.parse(userCreateDto.birth),
            timestampCreated = OffsetDateTime.now()
        )

        repository.saveAndFlush(user)

        return user
    }
}