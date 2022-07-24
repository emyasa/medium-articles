package com.emyasa;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AddAuthHeaderGlobalPreFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String hypotheticalBearerToken = "sample-bearer-token";

        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header("Authorization", "Bearer " + hypotheticalBearerToken)
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate().request(request).build();

        return chain.filter(mutatedExchange);
    }
}
