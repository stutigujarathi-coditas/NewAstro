package NewAstroConnectApp.NewAstroConnect.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Java 21 Spring Boot Swagger API")
                        .version("1.0.0")
                        .description("API documentation for Java 21 Spring Boot application")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}