//package com.order.gatewayservice.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class JwtAuthenticationFilter implements GlobalFilter {
//    private final String secretKey = "mySecretKey"; // UserService와 동일한 키 사용
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String path = exchange.getRequest().getPath().toString();
//
//        // 인증이 필요 없는 경로는 필터를 건너뜁니다.
//        if (path.startsWith("/auth") || path.startsWith("/public")) {
//            return chain.filter(exchange);
//        }
//
//        // JWT 토큰 검증 로직
//        String token = extractTokenFromRequest(exchange.getRequest());
//        if (token == null || !validateToken(token)) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//        return chain.filter(exchange);
//    }
//
//    private String extractTokenFromRequest(ServerHttpRequest request) {
//        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        return (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
//    }
//
//    private boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}