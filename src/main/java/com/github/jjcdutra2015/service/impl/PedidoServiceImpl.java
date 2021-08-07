package com.github.jjcdutra2015.service.impl;

import com.github.jjcdutra2015.domain.entity.Cliente;
import com.github.jjcdutra2015.domain.entity.ItemPedido;
import com.github.jjcdutra2015.domain.entity.Pedido;
import com.github.jjcdutra2015.domain.entity.Produto;
import com.github.jjcdutra2015.domain.enums.StatusPedido;
import com.github.jjcdutra2015.domain.repository.ClienteRepository;
import com.github.jjcdutra2015.domain.repository.ItemPedidoRespository;
import com.github.jjcdutra2015.domain.repository.PedidoRepository;
import com.github.jjcdutra2015.domain.repository.ProdutoRepository;
import com.github.jjcdutra2015.exception.PedidoNaoEncontradoException;
import com.github.jjcdutra2015.exception.RegraNegocioException;
import com.github.jjcdutra2015.rest.dto.ItemPedidoDTO;
import com.github.jjcdutra2015.rest.dto.PedidoDTO;
import com.github.jjcdutra2015.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepositoryRepository;
    private final ProdutoRepository produtoRepositoryRepository;
    private final ItemPedidoRespository itemPedidoRespositoryRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Cliente cliente = clienteRepositoryRepository
                .findById(dto.getCliente())
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = converterItem(pedido, dto.getItems());
        repository.save(pedido);
        itemPedidoRespositoryRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map(p -> {
                    p.setStatus(statusPedido);
                    return repository.save(p);
                })
                .orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItem(Pedido pedido, List<ItemPedidoDTO> itens) {
        if (itens.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        }

        return itens.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtoRepositoryRepository
                    .findById(idProduto)
                    .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());

    }
}
