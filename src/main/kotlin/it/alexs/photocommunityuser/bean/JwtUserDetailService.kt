package it.alexs.photocommunityuser.bean

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailService(
    private val userService: UserService
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.getByEmail(username)

        return User(username, user.password, emptyList())
    }
}