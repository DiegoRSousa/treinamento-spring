package com.indra.treinamento.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.indra.treinamento.model.Categoria;
import com.indra.treinamento.model.Produto;
import com.indra.treinamento.model.TipoTributacao;
import com.indra.treinamento.repository.CategoriaRepository;
import com.indra.treinamento.valid.Exists;
import com.sun.istack.NotNull;

public class ProdutoRequest {

	@NotBlank
	private String descricao;
	@Positive
	private BigDecimal preco;
	@NotNull
	private TipoTributacao tipoTributacao;
	@PositiveOrZero
	private BigDecimal aliquotaImposto;
	@Positive
	@Exists(domainClass = Categoria.class, fieldName = "id")
	private Long categoriaId;
	
	@Deprecated
	public ProdutoRequest() {}
	
	public ProdutoRequest(String descricao, BigDecimal preco, TipoTributacao tipoTributacao, 
			BigDecimal aliquotaImposto, Long categoriaId) {
		this.descricao = descricao;
		this.preco = preco;
		this.tipoTributacao = tipoTributacao;
		this.aliquotaImposto = aliquotaImposto;
		this.categoriaId = categoriaId;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public TipoTributacao getTipoTributacao() {
		return tipoTributacao;
	}

	public BigDecimal getAliquotaImposto() {
		return aliquotaImposto;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public Produto toModel(CategoriaRepository categoriaRepository) {
		var categoria = categoriaRepository.getById(categoriaId);
		return new Produto(descricao, preco, tipoTributacao, aliquotaImposto, categoria);
	}
	
}
