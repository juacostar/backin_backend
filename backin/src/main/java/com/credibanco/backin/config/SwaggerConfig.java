package com.credibanco.backin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Credibanco Backin API")
                        .version("1.0.0")
                        .description("API para gestionar operaciones de Credibanco Backin")
                        .termsOfService("https://www.credibanco.com/terms")
                        .contact(new Contact()
                                .name("Soporte Credibanco")
                                .email("soporte@credibanco.com")
                                .url("https://www.credibanco.com"))
                        .license(new License()
                                .name("Licencia Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0"))
                );
    }
}
