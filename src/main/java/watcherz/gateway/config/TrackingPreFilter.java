package watcherz.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class TrackingPreFilter implements GlobalFilter, Ordered {

    private static final String TRACKING_ID = "X-TRACKING-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Tracking filter invoked.....");
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        if(!hasTrackingID(headers))
        {
            request = exchange.getRequest()
                    .mutate()
                    .header(TRACKING_ID, UUID.randomUUID().toString())
                    .build();
            return chain.filter(exchange.mutate().request(request).build());
        }
        else {
            log.info(String.format("Tracked request with tracking id %s", headers.get(TRACKING_ID)));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean hasTrackingID(HttpHeaders headers)
    {
        return headers.containsKey(TRACKING_ID);
    }
}