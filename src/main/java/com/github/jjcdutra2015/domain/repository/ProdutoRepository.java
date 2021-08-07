package com.github.jjcdutra2015.domain.repository;

import com.github.jjcdutra2015.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
