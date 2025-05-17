package ba.unsa.etf.gateway.logging;

import ba.unsa.etf.gateway.SystemEventServiceOuterClass;
import ba.unsa.etf.gateway.grpc.SystemEventServiceImpl;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RequiredArgsConstructor
@Component
public class LoggingFilter implements GlobalFilter, Ordered {
    private final SystemEventServiceImpl systemEventService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logSystemEvent(exchange);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // The order of this filter. Lower value means higher priority.
        return -1;
    }

    private void logSystemEvent(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        var url = request.getURI().toString();
        var method = request.getMethod().toString();
        System.out.println("Logging system event");
        System.out.println(request);
        System.out.println(request.getHeaders());

        SystemEventServiceOuterClass.SystemEvent systemEventRequest = SystemEventServiceOuterClass.SystemEvent.newBuilder()
                .setTimestamp(Instant.now().toString())
                .setMicroserviceName("unknown")
                .setUser("unknown")
                .setActionType(method)
                .setResourceName(url)
                .setResponseType("unknown")
                .build();

        systemEventService.logSystemEvent(systemEventRequest, new StreamObserver<>() {
            @Override
            public void onNext(SystemEventServiceOuterClass.SystemEventResponse response) {
                System.out.println("Received response: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error occurred: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("RPC call completed");
            }
        });
    }
}