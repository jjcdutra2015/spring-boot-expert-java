package com.github.jjcdutra2015.rest.controller;

import com.github.jjcdutra2015.exception.PedidoNaoEncontradoException;
import com.github.jjcdutra2015.exception.RegraNegocioExcepetion;
import com.github.jjcdutra2015.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioExcepetion.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioExcepetion ex) {
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNaoEncontradoException(PedidoNaoEncontradoException ex) {
        return new ApiErrors(ex.getMessage());
    }
}
