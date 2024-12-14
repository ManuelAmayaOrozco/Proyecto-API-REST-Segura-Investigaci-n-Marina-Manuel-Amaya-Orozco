package com.es.api_investigacion_marina.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
Controlador de excepciones el cual devuelve un tipo de excepción diferente dependiendo
de la situación.
 */
@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageForClient handleBadRequest(HttpServletRequest request, Exception e) {

        return new ErrorMessageForClient(e.getMessage(), request.getRequestURI());

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageForClient handleNotFound(HttpServletRequest request, Exception e) {

        return new ErrorMessageForClient(e.getMessage(), request.getRequestURI());

    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessageForClient handleInternalServerError(HttpServletRequest request, Exception e) {

        return new ErrorMessageForClient(e.getMessage(), request.getRequestURI());

    }

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorMessageForClient handleNotAuthorizedException(HttpServletRequest request, Exception e) {

        return new ErrorMessageForClient(e.getMessage(), request.getRequestURI());

    }

}
