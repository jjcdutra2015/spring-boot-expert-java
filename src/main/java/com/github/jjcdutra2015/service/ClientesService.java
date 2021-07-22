package com.github.jjcdutra2015.service;

import com.github.jjcdutra2015.model.Cliente;
import com.github.jjcdutra2015.repository.ClientesRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {

    private ClientesRepository repository;

    public ClientesService(ClientesRepository repository) {
        this.repository = repository;
    }

    public void salvarCliente(Cliente cliente) {
        validarCliente(cliente);
        repository.persist(cliente);
    }

    public void validarCliente(Cliente cliente) {
        // validar cliente
    }
}
