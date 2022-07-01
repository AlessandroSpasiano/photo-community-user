package it.alexs.photocommunityuser.dtos

import it.alexs.photocommunityuser.entities.User
import java.time.format.DateTimeFormatter

data class UserDto(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val birth: String
) {
    companion object {

        fun createFrom(user: User): UserDto {
            return UserDto(
                id = user.id,
                email = user.email,
                lastName = user.lastName,
                firstName = user.firstName,
                birth = user.birth.format(DateTimeFormatter.ISO_LOCAL_DATE)
            )
        }
    }
}
