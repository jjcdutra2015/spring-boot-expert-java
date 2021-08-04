package com.github.jjcdutra2015.service;

import com.github.jjcdutra2015.domain.entity.Pedido;
import com.github.jjcdutra2015.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
}
