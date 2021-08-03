package com.github.jjcdutra2015.rest.controller;

import com.github.jjcdutra2015.domain.entity.Pedido;
import com.github.jjcdutra2015.rest.dto.PedidoDTO;
import com.github.jjcdutra2015.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer salvar(@RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }
}
