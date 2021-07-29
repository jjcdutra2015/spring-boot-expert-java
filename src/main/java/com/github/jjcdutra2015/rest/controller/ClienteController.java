package com.github.jjcdutra2015.rest.controller;

import com.github.jjcdutra2015.domain.entity.Cliente;
import com.github.jjcdutra2015.domain.repository.Clientes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> getClienteByid(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.of(cliente);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/clientes")
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }
}
