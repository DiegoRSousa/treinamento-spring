package com.indra.treinamento.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sun.istack.NotNull;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String descricao;
	@NotNull
	@Positive
	private BigDecimal preco;
	@NotNull
	private TipoTributacao tipoTributacao;
	@NotNull
	@PositiveOrZero
	private BigDecimal aliquotaImposto;
	@NotNull
	@ManyToOne
	private Categoria categoria;
	private LocalDateTime criadoEm = LocalDateTime.now();
	private LocalDateTime atualizadoEm;
	
	@Deprecated
	public Produto() { }
	
	public Produto(@NotBlank String descricao, @Positive BigDecimal preco, @NotNull TipoTributacao tipoTributacao,
			@PositiveOrZero BigDecimal aliquotaImposto, @NotNull Categoria categoria) {
		this.descricao = descricao;
		this.preco = preco;
		this.tipoTributacao = tipoTributacao;
		this.aliquotaImposto = aliquotaImposto;
		this.categoria = categoria;
	}
	
	public void update(Produto produto) {
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
		this.tipoTributacao = produto.getTipoTributacao();
		this.aliquotaImposto = produto.getAliquotaImposto();
		this.categoria = produto.getCategoria();
		this.atualizadoEm = LocalDateTime.now();
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
	

	public Categoria getCategoria() {
		return categoria;
	}

	public LocalDateTime getAtualizadoEm() {
		return atualizadoEm;
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}	
}
