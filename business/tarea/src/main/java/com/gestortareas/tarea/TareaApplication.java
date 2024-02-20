package com.gestortareas.tarea;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class TareaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TareaApplication.class, args);
	}

	@Bean
	public GroupedOpenApi groupedOpenApi() {
		return GroupedOpenApi.builder()
				.group("tareas")
				.addOpenApiCustomizer(openApi -> openApi.info(new Info().title("API de Tareas").description("API para las operaciones CRUD sobre las tareas")))
				.build();
	}

}
