package it.alexs.photocommunityuser.endpoints

import it.alexs.photocommunityuser.bean.UserService
import it.alexs.photocommunityuser.dtos.UserCreateDto
import it.alexs.photocommunityuser.dtos.UserDto
import it.alexs.photocommunityuser.dtos.UserUpdateDto
import it.alexs.photocommunityuser.utils.criteria.RequestCriteria
import it.alexs.photocommunityuser.utils.wrappers.ResponseWrapper
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserResource(
    private val service: UserService
) {

    private val logger = LoggerFactory.getLogger(UserResource::class.java)

    @GetMapping
    fun getAllUsers(@Valid requestCriteria: RequestCriteria = RequestCriteria()): ResponseWrapper<UserDto> {
        val paging = PageRequest.of(requestCriteria.offset, requestCriteria.limit, requestCriteria.toSort())

        val all = service.getAll(paging)

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
    fun updateUser(@PathVariable("id") id: Long, @RequestBody @Valid userUpdate: UserUpdateDto){
        service.updateUser(id, userUpdate)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody @Valid userCreate: UserCreateDto): UserDto {
        return UserDto.createFrom(service.createUser(userCreate))
    }

}