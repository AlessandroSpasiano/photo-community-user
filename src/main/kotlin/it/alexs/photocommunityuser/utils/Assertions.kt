package it.alexs.photocommunityuser.utils

import it.alexs.photocommunityuser.exceptions.UserConflictException
import it.alexs.photocommunityuser.exceptions.UserNotFoundException

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