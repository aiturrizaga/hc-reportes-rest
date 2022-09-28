package com.sisuz.reportes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST services")
                        .description("Especificacion de REST API services")
                        .license(new License().name("Financiera Oh").url("https://tarjetaoh.pe"))
                        .version("1.0")
                );
    }

}
