package com.indra.treinamento.dto;

import javax.validation.constraints.NotBlank;

import com.indra.treinamento.model.Categoria;

public class CategoriaRequest {

	@NotBlank
	private String descricao;
	
	@Deprecated
	public CategoriaRequest() {}
	public CategoriaRequest(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public Categoria toModel() {
		return new Categoria(descricao);
	}
	
}
