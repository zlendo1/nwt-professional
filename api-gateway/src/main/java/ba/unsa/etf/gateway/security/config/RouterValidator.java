package ba.unsa.etf.gateway.security.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    /**
     * List of endpoints that do not require authentication.
     * Requests to these paths will bypass the AuthenticationFilter.
     */
    public static final List<String> openApiEndpoints = List.of(
            "/api/user/register", // Example registration endpoint
            "/api/user/login",    // Example login endpoint
            // Add other public endpoints here if needed
            // Example: "/api/public/some-resource"
            "/eureka" // Eureka endpoints might need to be open for service registration depending on setup
    );

    /**
     * Predicate that tests if a ServerHttpRequest should be secured (requires authentication).
     * It returns true if the request path does NOT contain any of the openApiEndpoints.
     */
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
    // Alternative using startWith (more precise):
    // request -> openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().startsWith(uri));

}