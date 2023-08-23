package com.rediense.todoapp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class OpenAPIConfig {

    @Value("http://localhost:8080/todo-api") //${dev-url} usar variables
    private String devUrl;

//    @Value("${bezkoder.openapi.prod-url}")
//    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

//        Server prodServer = new Server();
//        prodServer.setUrl(prodUrl);
//        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("ballesta.esneider@gmail.com");
        contact.setName("rediensE");
        contact.setUrl("https://github.com/UdeA-rediensE");

//        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Todo-App API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints for Todo App");
//                .termsOfService("https://www.bezkoder.com/terms")
//                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}