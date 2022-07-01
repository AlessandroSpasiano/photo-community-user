package it.alexs.photocommunityuser.entities

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
    var id: Long,

    @NotEmpty
    var email: String,

    @NotEmpty
    var firstName: String,

    @NotEmpty
    var lastName: String,

    @NotEmpty
    var password: String,

    @Past
    var birth: LocalDate,

    @FutureOrPresent
    var timestampCreated: OffsetDateTime,

    @FutureOrPresent
    var timestampModified: OffsetDateTime? = null
)
