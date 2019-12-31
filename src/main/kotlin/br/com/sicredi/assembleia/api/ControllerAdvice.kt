package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AssembeiaValidationException::class)
    fun handleAssembeiaValidationException(ex: AssembeiaValidationException): ErrorResponse {
        val errorResponse = ErrorResponse(status = HttpStatus.BAD_REQUEST.name, errorMessage = ex.message)
        return errorResponse
    }
}

data class ErrorResponse(
    val status: String,
    val errorMessage: String
)
