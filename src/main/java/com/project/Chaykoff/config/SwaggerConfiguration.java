package com.project.Chaykoff.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SwaggerConfiguration {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setName("Chaykoff website");

        Info info = new Info()
                .title("Chaykoff API")
                .version("1.0")
                .contact(contact)
                .description("OpenAPI documentation");

        return new OpenAPI().info(info);
    }
}