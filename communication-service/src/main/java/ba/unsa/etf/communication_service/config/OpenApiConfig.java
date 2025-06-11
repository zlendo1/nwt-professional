package ba.unsa.etf.communication_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  protected OpenAPI openAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Communication Services API")
                .version("1.0.0")
                .description(
                    "The communication service API is used to communicate with the messaging"
                        + " backend the nwt-professional application.")
                .contact(new Contact().name("Zijad Lendo").email("zlendo1@etf.unsa.ba")));
  }
}
