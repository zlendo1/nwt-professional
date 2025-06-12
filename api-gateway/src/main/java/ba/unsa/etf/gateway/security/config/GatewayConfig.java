package ba.unsa.etf.gateway.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class GatewayConfig {
    private final AuthenticationFilter filter; // Pretpostavljamo da želite primijeniti JWT filter

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Ruta za test-skills-service (već imate)
                .route("test-skills-service", r -> r.path("/api/test/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://test-skills-service"))

                // NOVA RUTA ZA JOB-SERVICE
                .route("job-service", r -> r.path("/api/job/**") // Definišite putanju na Gatewayu za job-service
                        .filters(f -> f.filter(filter)) // Primijenite isti filter (ili drugi po potrebi)
                        .uri("lb://job-service")) // Proslijedi na servis registrovan u Eureki pod imenom "job-service"

                .route("user-management-service", r -> r.path("/api/user/**") // All requests to /api/users/...
                        .filters(f -> f.filter(filter)) // Apply authentication filter
                        .uri("lb://user-management-service")) // Forward to the user service

                .build();
    }
}
