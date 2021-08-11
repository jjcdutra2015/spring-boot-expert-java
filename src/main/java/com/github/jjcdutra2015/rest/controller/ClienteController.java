package com.github.jjcdutra2015.rest.controller;

import com.github.jjcdutra2015.domain.entity.Cliente;
import com.github.jjcdutra2015.domain.repository.ClienteRepository;
import io.swagger.annotations.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Api("Api de Clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o id informado")
    })
    public Cliente getClienteByid(@PathVariable
                                  @ApiParam("Id do cliente") Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Cliente inválido")
    })
    public Cliente save(@RequestBody @Valid Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
        clienteRepository.findById(id).map(clienteExistente -> {
            cliente.setId(clienteExistente.getId());
            clienteRepository.save(cliente);
            return cliente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping
    public List<Cliente> find(Cliente clienteFiltro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cliente> example = Example.of(clienteFiltro, matcher);
        return clienteRepository.findAll(example);
    }
}
