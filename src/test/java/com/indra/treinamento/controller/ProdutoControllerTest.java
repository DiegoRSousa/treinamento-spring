package com.indra.treinamento.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.indra.treinamento.controller.handler.StandardError;
import com.indra.treinamento.controller.handler.ValidationError;
import com.indra.treinamento.dto.ProdutoRequest;
import com.indra.treinamento.dto.ProdutoResponse;
import com.indra.treinamento.model.TipoTributacao;
import com.indra.treinamento.util.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class ProdutoControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	private final String url = "/produtos";

	@Test
	@DisplayName("deve cadastrar quando todos os dados validos")
	void test1() {
		var request = new ProdutoRequest("Laranja", new BigDecimal(4), TipoTributacao.TRIBUTAVEL, new BigDecimal(10),
				1L);

		var response = testRestTemplate.postForEntity(url, request, ProdutoResponse.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(response.getBody().getId() > 0);
	}

	@Test
	@DisplayName("não deve cadastrar quando categoria não existir")
	void test2() {
		var request = new ProdutoRequest("Laranja", new BigDecimal(4), TipoTributacao.TRIBUTAVEL, new BigDecimal(10),
				2L);

		var response = testRestTemplate.postForEntity(url, request, ValidationError.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(1, response.getBody().getErrors().size());
		assertEquals("categoriaId", response.getBody().getErrors().get(0).getFieldName());
		assertEquals("não existe", response.getBody().getErrors().get(0).getMessage());

	}

	@Test
	@DisplayName("deve encontrar o produto por id")
	void test3() {
		var response = testRestTemplate.exchange(url + "/1", HttpMethod.GET, null, ProdutoResponse.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody().getId());
	}

	@Test
	@DisplayName("não deve encontrar o produto por id")
	void test4() {
		var response = testRestTemplate.exchange(url + "/10", HttpMethod.GET, null, StandardError.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Not found", response.getBody().getError());
	}

	@Test
	@DisplayName("deve encontrar todos os produtos que contenham parte da descrição")
	void test5() {
		var response = testRestTemplate.exchange(url + "/find/a", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ProdutoResponse>>() {
				});

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
		assertEquals("Laranja", response.getBody().get(0).getDescricao());
	}

	@Test
	@DisplayName("não deve encontrar produtos quando não existir com a descrição informada")
	void test6() {
		var response = testRestTemplate.exchange(url + "/find/b", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ProdutoResponse>>() {
				});

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, response.getBody().size());
	}

	@Test
	@DisplayName("deve encontrar todos os produtos cadastrados")
	void test7() {
		var response = testRestTemplate.exchange(url, HttpMethod.GET, null,  
				new ParameterizedTypeReference<PageableResponse<ProdutoResponse>>() {
		});
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody().getContent());
		assertTrue(response.getBody().isFirst());
		assertNotNull(response.getBody().getContent().get(0).getDescricao());
	}
	
	@Test
	@DisplayName("deve atualizar o produto quando todos os dados validos")
	void test8() {
		var request = new ProdutoRequest("Maçã", new BigDecimal("4.00"), TipoTributacao.ISENTO, new BigDecimal("0.00"), 1L);
		
		var response = testRestTemplate.exchange(url+"/1", HttpMethod.PUT, new HttpEntity<>(request), ProdutoResponse.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody().getId());
		assertEquals("Maçã", response.getBody().getDescricao());
		assertNotNull(response.getBody().getAtualizadoEm());
	}
	
	@Test
	@DisplayName("deve deletar quando produto existir")
	void test9() {
		var response = testRestTemplate.exchange(url+"/1", HttpMethod.DELETE, null, Void.class);
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
	}

}
