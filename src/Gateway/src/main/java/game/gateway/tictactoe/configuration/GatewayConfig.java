package game.gateway.tictactoe.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/game/**")
                        .uri("http://game-service:8081")
                )
                .route(p -> p
                        .path("/api/auth/**")
                        .uri("http://auth-service:8082")
                )
                .build();
    }
}
