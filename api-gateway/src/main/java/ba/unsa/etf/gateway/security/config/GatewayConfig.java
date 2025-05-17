package ba.unsa.etf.gateway.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class GatewayConfig {
    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("test-skills-service", r -> r.path("/api/test/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://test-skills-service"))
                .build();
    }

}

/*
.route("user-management-service", r -> r.path("/api/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-management-service"))
 */