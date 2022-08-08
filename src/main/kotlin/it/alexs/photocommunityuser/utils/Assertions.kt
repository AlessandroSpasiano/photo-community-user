package it.alexs.photocommunityuser.utils

import it.alexs.photocommunityuser.exceptions.UserConflictException
import it.alexs.photocommunityuser.exceptions.UserNotFoundException
import it.alexs.photocommunityuser.exceptions.UserUnauthorizedException

fun assertOrNotFound(condition: Boolean, message: String) {
    if (!condition) {
        throw UserNotFoundException(message)
    }
}

fun assertOrConflict(condition: Boolean, message: String) {
    if (!condition) {
        throw UserConflictException(message)
    }
}

fun assertOrUnauthorized(condition: Boolean, message: String) {
    if (!condition) {
        throw UserUnauthorizedException(message)
    }
}