package it.alexs.photocommunityuser.config

import it.alexs.photocommunityuser.bean.JwtUserDetailService
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val jwtUserDetailService: JwtUserDetailService
): OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(JwtRequestFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestTokenHeader = request.getHeader("Authorization")
        var username: String? = null
        var jwtToken: String? = null
        if (requestTokenHeader.isNullOrEmpty()) {
            logger.error("JWT token doeas not begin with Bearer string")
        } else {
            jwtToken = requestTokenHeader.substring(7)
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken)
            } catch (e: Exception) {
                logger.error(e.localizedMessage)
            }
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = jwtUserDetailService.loadUserByUsername(username)

            if(jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                val autenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

                autenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = autenticationToken
            }
        }

        filterChain.doFilter(request, response)
    }
}