package com.example.apiBicoCerto;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiBicoCertoApplication {

	public static void main(String[] args) {
		// Carrega o .env manualmente
		Dotenv dotenv = Dotenv.configure()
				.directory("./") // Garante que olha na raiz
				.ignoreIfMalformed()
				.ignoreIfMissing()
				.load();

		// Injeta as variáveis do .env nas Propriedades do Sistema do Java
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		SpringApplication.run(ApiBicoCertoApplication.class, args);
	}

}
