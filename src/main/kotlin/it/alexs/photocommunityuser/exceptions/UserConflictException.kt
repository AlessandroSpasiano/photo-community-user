package it.alexs.photocommunityuser.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class UserConflictException(message: String) : UserException(message)
