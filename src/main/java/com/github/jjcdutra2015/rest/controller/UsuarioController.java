package com.github.jjcdutra2015.rest.controller;

import com.github.jjcdutra2015.domain.entity.Usuario;
import com.github.jjcdutra2015.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Usuario salvar(@Valid @RequestBody Usuario usuario) {
        return service.salvar(usuario);
    }
}
