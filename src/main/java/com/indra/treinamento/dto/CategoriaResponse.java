package com.indra.treinamento.dto;

import com.indra.treinamento.model.Categoria;

public class CategoriaResponse {

	private Long id;
	private String descricao;
	
	@Deprecated
	public CategoriaResponse() {}
	
	public CategoriaResponse(Categoria categoria) {
		this.id = categoria.getId();
		this.descricao = categoria.getDescricao();
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}	
}
