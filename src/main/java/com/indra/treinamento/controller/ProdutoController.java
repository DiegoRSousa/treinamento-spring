package com.indra.treinamento.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.indra.treinamento.dto.ProdutoRequest;
import com.indra.treinamento.dto.ProdutoResponse;
import com.indra.treinamento.model.Produto;
import com.indra.treinamento.repository.CategoriaRepository;
import com.indra.treinamento.repository.ProdutoRepository;
import com.indra.treinamento.valid.AliquotaImpostoValidator;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private CategoriaRepository categoriaRepository;
	private ProdutoRepository produtoRepository;
	private AliquotaImpostoValidator aliquotaImpostoValidator;
	
	public ProdutoController(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository,
			AliquotaImpostoValidator aliquotaImpostoValidator) {
		this.categoriaRepository = categoriaRepository;
		this.produtoRepository = produtoRepository;
		this.aliquotaImpostoValidator = aliquotaImpostoValidator;
	}
	
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(aliquotaImpostoValidator);
	}

	@PostMapping
	public ResponseEntity<ProdutoResponse> save(@RequestBody @Valid ProdutoRequest request) {
		var produto = request.toModel(categoriaRepository);
		produtoRepository.save(produto);
		return new ResponseEntity<>(new ProdutoResponse(produto), HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ProdutoResponse> find(@PathVariable Long id) {
		var produto = findById(id);
		return ResponseEntity.ok(new ProdutoResponse(produto));
	}
	
	@GetMapping("find/{descricao}")
	public ResponseEntity<List<ProdutoResponse>> findByDescricao(@PathVariable String descricao) {
		var produtos = produtoRepository.findByDescricaoContainingIgnoreCase(descricao);
		return ResponseEntity.ok(produtos.stream().map(ProdutoResponse::new).collect(Collectors.toList()));
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoResponse>> findAll(
			@PageableDefault(size = 4, page = 0, sort = "id", direction = Direction.DESC) 
			Pageable pageable) {
		var produtos = produtoRepository.findAll(pageable);
		return ResponseEntity.ok(produtos.map(ProdutoResponse::new));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ProdutoResponse> update(@PathVariable Long id, @RequestBody @Valid ProdutoRequest request) {
		var produto = findById(id);
		produto.update(request.toModel(categoriaRepository));
		produtoRepository.save(produto);
		return ResponseEntity.ok(new ProdutoResponse(produto));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		var produto = findById(id);
		produtoRepository.delete(produto);
		return ResponseEntity.noContent().build();
	}
	
	private Produto findById(Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> 
			new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
}
