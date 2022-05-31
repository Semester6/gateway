package watcherz.gateway.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import watcherz.gateway.config.AuthenticationFilter;

@Configuration
@CrossOrigin(origins = "*")
public class RouteBuilder {

    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/movie/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://movieservice"))
//                .route(r -> r
//                        .path("/user/**")
//                        .filters(f -> f.addRequestHeader("something", "something"))
//                        .uri("lb://USER-SERVICE"))
                .route(r -> r
                        .path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://authservice"))
                .build();
    }
}
