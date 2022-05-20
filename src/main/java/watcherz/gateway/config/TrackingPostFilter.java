package watcherz.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Slf4j
@Component
public class TrackingPostFilter implements GlobalFilter, Ordered {

    private static final String TRACKING_ID = "X-TRACKING-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Injecting tracking into response...");

        HttpHeaders header = exchange.getRequest().getHeaders();

        MDC.put(TRACKING_ID, getTrackingId(header));

        exchange.getResponse().getHeaders().add(TRACKING_ID, getTrackingId(header));

        return chain.filter(exchange);
    }

    private String getTrackingId(HttpHeaders header){
        return Objects.requireNonNull(header.get(TRACKING_ID)).iterator().next();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}