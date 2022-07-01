package it.alexs.photocommunityuser.entities

import net.bytebuddy.implementation.bind.annotation.Empty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.OffsetDateTime
import javax.persistence.*
import javax.validation.constraints.FutureOrPresent
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Past

@Entity
@Table(
    name = "users",
    uniqueConstraints = [UniqueConstraint(columnNames = ["email"])],
    indexes = [Index(columnList = "email")]
)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @NotEmpty
    val email: String,

    @NotEmpty
    val firstName: String,

    @NotEmpty
    val lastName: String,

    @NotEmpty
    val password: String,

    @Past
    val birth: LocalDate,

    @FutureOrPresent
    val timestampCreated: OffsetDateTime,

    @FutureOrPresent
    val timestampModified: OffsetDateTime? = null
)
