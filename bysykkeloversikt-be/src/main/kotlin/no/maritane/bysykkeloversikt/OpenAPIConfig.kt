package no.maritane.bysykkeloversikt

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition
@Configuration
class OpenAPIConfig {

    @Bean
    fun bysykkeloversiktOpenAPI(): OpenAPI? {
        return OpenAPI()
            .info(
                Info().title("Bysykkeloversikt")
                    .description("Internt API for bysykkeloversikt")
                    .version("v0.0.1")
            )
    }
}