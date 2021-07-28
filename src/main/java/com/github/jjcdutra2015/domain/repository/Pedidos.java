package com.github.jjcdutra2015.domain.repository;

import com.github.jjcdutra2015.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
}
