package it.alexs.photocommunityuser.endpoints

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.util.WebUtils
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionControllerAdvice(private val errorAttributes: ErrorAttributes): ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        val error = createErrorBody(request, status, ex)
        error?.put("errors", createErrorDetails(ex))

        return super.handleExceptionInternal(ex, error, headers, status, request)
    }

    private fun createErrorBody(
        request: WebRequest,
        status: HttpStatus,
        ex: MethodArgumentNotValidException
    ): MutableMap<String, Any>? {
        val httpServletRequest: HttpServletRequest = asHttpServletRequest(request)
        httpServletRequest.setAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE, status.value())
        WebUtils.exposeErrorRequestAttributes(httpServletRequest, ex, null)

        return errorAttributes.getErrorAttributes(
            request,
            ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE)
        )
    }

    private fun createErrorDetails(ex: MethodArgumentNotValidException): Map<String, List<String?>?> {
        return ex.bindingResult.allErrors.fold(HashMap<String, MutableList<String?>?>()) { allErrors, fieldError ->
            allErrors.apply {
                val fieldName = (fieldError as FieldError).field
                val errorMessage = fieldError.defaultMessage
                if (this[fieldName] == null) {
                    this[fieldName] = mutableListOf()
                }
                this[fieldName]?.add(errorMessage)
            }
        }
    }

    private fun asHttpServletRequest(request: WebRequest): HttpServletRequest {
        return (request as NativeWebRequest).nativeRequest as HttpServletRequest
    }
}