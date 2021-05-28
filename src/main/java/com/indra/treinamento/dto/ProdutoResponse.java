package com.indra.treinamento.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.indra.treinamento.model.Produto;
import com.indra.treinamento.model.TipoTributacao;

public class ProdutoResponse {

	private Long id;
	private String descricao;
	private BigDecimal preco;
	private TipoTributacao tipoTributacao;
	private BigDecimal aliquotaImposto;
	private String descricaoCategoria;
	private LocalDateTime criadoEm;
	private LocalDateTime atualizadoEm;
	
	@Deprecated
	public ProdutoResponse() {}
	public ProdutoResponse(Produto produto) {
		this.id = produto.getId();
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
		this.tipoTributacao = produto.getTipoTributacao();
		this.aliquotaImposto = produto.getAliquotaImposto();
		this.descricaoCategoria = produto.getCategoria().getDescricao();
		this.criadoEm = produto.getCriadoEm();
		this.atualizadoEm = produto.getAtualizadoEm();
	}

	public Long getId() {
		return id;
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

	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}
	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}
	public LocalDateTime getAtualizadoEm() {
		return atualizadoEm;
	}
		
}
