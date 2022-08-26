package it.alexs.photocommunityuser.endpoints

import it.alexs.photocommunityuser.bean.UserService
import it.alexs.photocommunityuser.config.JwtTokenUtil
import it.alexs.photocommunityuser.dtos.*
import it.alexs.photocommunityuser.utils.assertOrUnauthorized
import it.alexs.photocommunityuser.utils.criteria.RequestCriteria
import it.alexs.photocommunityuser.utils.specifications.UserSpecification
import it.alexs.photocommunityuser.utils.wrappers.ResponseWrapper
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserResource(
    private val service: UserService,
    private val jwtTokenUtil: JwtTokenUtil
) {

    private val logger = LoggerFactory.getLogger(UserResource::class.java)

    @PostMapping("/authenticate")
    fun authenticateUser(@RequestBody jwtBody: JWTRequest): JWTDto {
        val userByUsername = service.getByEmail(jwtBody.username)

        assertOrUnauthorized(userByUsername.password == jwtBody.password, "Unauthorized")
        val at = jwtTokenUtil.generateToken(userByUsername)
        val rt = jwtTokenUtil.generateRefreshToken(userByUsername)

        return JWTDto(at, rt)
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody jwtBody: JWTRefreshRequest): JWTDto {
        val usernameFromToken = jwtTokenUtil.getUsernameFromToken(jwtBody.refreshToken)

        val userByUsername = service.getByEmail(usernameFromToken)

        val isValid = jwtTokenUtil.validateToken(
            jwtBody.refreshToken,
            User(userByUsername.email, userByUsername.password, emptyList())
        )

        assertOrUnauthorized(isValid, "Unauthorized")

        val at = jwtTokenUtil.generateToken(userByUsername)
        val rt = jwtTokenUtil.generateRefreshToken(userByUsername)

        return JWTDto(at, rt)
    }

    @GetMapping
    fun getAllUsers(@Valid requestCriteria: RequestCriteria = RequestCriteria()): ResponseWrapper<UserDto> {
        val searchCriteria = requestCriteria.toSearchCriteria()
        val paging = PageRequest.of(requestCriteria.offset, requestCriteria.limit, requestCriteria.toSort())

        val all = if (searchCriteria == null) {
            service.getAll(paging, null)
        } else {
            val spec = UserSpecification(searchCriteria)
            service.getAll(paging, spec)
        }

        val content = all.content.map { UserDto.createFrom(it) }

        return ResponseWrapper(result = content, totalPages = all.totalPages, totalElements = all.totalElements)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long): UserDto {
        val user = service.getByID(id)

        return UserDto.createFrom(user)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateUser(@PathVariable("id") id: Long, @RequestBody @Valid userUpdate: UserUpdateDto) {
        service.updateUser(id, userUpdate)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody @Valid userCreate: UserCreateDto): UserDto {
        return UserDto.createFrom(service.createUser(userCreate))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable("id") id: Long) {
        service.deleteUser(id)
    }

}