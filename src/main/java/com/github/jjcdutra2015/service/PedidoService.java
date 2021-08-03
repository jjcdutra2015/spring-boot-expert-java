package com.github.jjcdutra2015.service;

import com.github.jjcdutra2015.domain.entity.Pedido;
import com.github.jjcdutra2015.rest.dto.PedidoDTO;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
}
