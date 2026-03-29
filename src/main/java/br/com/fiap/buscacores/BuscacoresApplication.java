package br.com.fiap.buscacores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BuscacoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuscacoresApplication.class, args);
	}

}
