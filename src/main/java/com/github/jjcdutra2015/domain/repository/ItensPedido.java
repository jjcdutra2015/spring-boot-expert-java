package com.github.jjcdutra2015.domain.repository;

import com.github.jjcdutra2015.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer> {
}
