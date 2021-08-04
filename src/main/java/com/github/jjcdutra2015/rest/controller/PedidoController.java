package com.github.jjcdutra2015.rest.controller;

import com.github.jjcdutra2015.domain.entity.ItemPedido;
import com.github.jjcdutra2015.domain.entity.Pedido;
import com.github.jjcdutra2015.domain.enums.StatusPedido;
import com.github.jjcdutra2015.rest.dto.AtualizaStatusPedidoDTO;
import com.github.jjcdutra2015.rest.dto.InformacaoItemPedidoDTO;
import com.github.jjcdutra2015.rest.dto.InformacoesPedidoDTO;
import com.github.jjcdutra2015.rest.dto.PedidoDTO;
import com.github.jjcdutra2015.service.PedidoService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer salvar(@RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id) {
        return service.obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody AtualizaStatusPedidoDTO dto) {
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converter(Pedido pedido) {
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.EMPTY_LIST;
        }

        return itens.stream().map(item -> InformacaoItemPedidoDTO
                .builder()
                .descricao(item.getProduto().getDescricao())
                .valorUnitario(item.getProduto().getPreco())
                .quandidade(item.getQuantidade())
                .build()
        ).collect(Collectors.toList());
    }
}
