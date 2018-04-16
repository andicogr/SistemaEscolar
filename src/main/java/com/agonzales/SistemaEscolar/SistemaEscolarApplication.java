package com.agonzales.SistemaEscolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.agonzales.SistemaEscolar")
@SpringBootApplication
@EnableJpaRepositories(basePackages="com.agonzales.SistemaEscolar.repository")
@EntityScan(basePackages="com.agonzales.SistemaEscolar.domain")
public class SistemaEscolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaEscolarApplication.class, args);
	}

}
