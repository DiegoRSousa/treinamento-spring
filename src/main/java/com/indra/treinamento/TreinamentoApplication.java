package com.indra.treinamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TreinamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreinamentoApplication.class, args);
	}

}
