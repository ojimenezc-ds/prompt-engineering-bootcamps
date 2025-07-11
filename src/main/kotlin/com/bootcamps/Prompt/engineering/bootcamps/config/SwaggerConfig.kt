package com.bootcamps.Prompt.engineering.bootcamps.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Prompt Engineering Bootcamps API")
                    .version("1.0.0")
                    .description("API documentation for Prompt Engineering Bootcamps application")
                    .contact(
                        Contact()
                            .name("Development Team")
                            .email("dev@bootcamps.com")
                    )
            )
    }
}
