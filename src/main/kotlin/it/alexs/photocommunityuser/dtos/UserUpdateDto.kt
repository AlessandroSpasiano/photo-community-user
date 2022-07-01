package it.alexs.photocommunityuser.dtos

import org.springframework.format.annotation.DateTimeFormat
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class UserUpdateDto(
    @get:NotEmpty
    @get:Email
    @get:Size(max = 50)
    val email: String = "",

    @get:NotEmpty
    val firstName: String = "",

    @get:NotEmpty
    val lastName: String = "",

    @get:NotEmpty
    @get:DateTimeFormat
    val birth: String = ""
)
