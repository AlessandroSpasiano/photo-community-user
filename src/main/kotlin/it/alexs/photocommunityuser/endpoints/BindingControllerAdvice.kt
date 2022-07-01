package it.alexs.photocommunityuser.endpoints

import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.InitBinder

@ControllerAdvice
class BindingControllerAdvice {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.initDirectFieldAccess()
    }
}