package com.indra.treinamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indra.treinamento.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	List<Produto> findByDescricaoContainingIgnoreCase(String descricao);

	List<Produto> findByDescricaoLikeIgnoreCase(String descricao);

}
