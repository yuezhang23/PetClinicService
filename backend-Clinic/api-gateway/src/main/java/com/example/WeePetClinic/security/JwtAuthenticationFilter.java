package main.java.com.example.WeePetClinic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration:86400000}")
    private long jwtExpiration;

    // Public endpoints that don't require authentication
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
        "/auth/login",
        "/auth/register", 
        "/auth/refresh",
        // Account-service public endpoints via gateway prefix
        "/accounts/auth/login",
        "/accounts/auth/register",
        "/accounts/auth/refresh",
        "/accounts/auth/legacy/login",
        "/accounts/auth/legacy/roles",
        "/health",
        "/public",
        "/actuator"
    );
    
    // Admin-only endpoints
    private static final List<String> ADMIN_PATHS = Arrays.asList(
        "/admin",
        "/clinic/admin",
        "/donor/admin",
        "/subscription/admin"
    );

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getPath().value();
            
            // Skip authentication for public endpoints
            if (isPublicPath(path)) {
                return chain.filter(exchange);
            }

            String token = getJwtFromRequest(request);
            
            if (!StringUtils.hasText(token)) {
                logger.warn("No JWT token found for path: {}", path);
                return onError(exchange, "No JWT token found", HttpStatus.UNAUTHORIZED);
            }

            try {
                Claims claims = validateAndParseToken(token);
                
                // Check if token is expired
                if (isTokenExpired(claims)) {
                    logger.warn("JWT token expired for user: {}", claims.getSubject());
                    return onError(exchange, "JWT token expired", HttpStatus.UNAUTHORIZED);
                }
                
                // Check role-based access for admin endpoints
                if (isAdminPath(path) && !hasAdminRole(claims)) {
                    logger.warn("Access denied to admin endpoint for user: {} with role: {}", 
                               claims.getSubject(), claims.get("role"));
                    return onError(exchange, "Access denied", HttpStatus.FORBIDDEN);
                }
                
                // Add user info to headers for downstream services
                ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-ID", claims.getSubject())
                    .header("X-User-Role", claims.get("role", String.class))
                    .header("X-User-Email", claims.get("email", String.class))
                    .header("X-User-Name", claims.get("name", String.class))
                    .header("X-Token-Issued-At", String.valueOf(claims.getIssuedAt().getTime()))
                    .build();
                
                logger.debug("JWT authentication successful for user: {} accessing: {}", 
                           claims.getSubject(), path);
                
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
                
            } catch (ExpiredJwtException e) {
                logger.warn("JWT token expired: {}", e.getMessage());
                return onError(exchange, "JWT token expired", HttpStatus.UNAUTHORIZED);
            } catch (MalformedJwtException e) {
                logger.warn("Malformed JWT token: {}", e.getMessage());
                return onError(exchange, "Invalid JWT token format", HttpStatus.UNAUTHORIZED);
            } catch (UnsupportedJwtException e) {
                logger.warn("Unsupported JWT token: {}", e.getMessage());
                return onError(exchange, "Unsupported JWT token", HttpStatus.UNAUTHORIZED);
            } catch (Exception e) {
                logger.error("JWT token validation error: {}", e.getMessage());
                return onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
            }
        };
    }

    private Claims validateAndParseToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(jwtSecret)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration() != null && 
               claims.getExpiration().getTime() < System.currentTimeMillis();
    }
    
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
    
    private boolean isAdminPath(String path) {
        return ADMIN_PATHS.stream().anyMatch(path::startsWith);
    }
    
    private boolean hasAdminRole(Claims claims) {
        String role = claims.get("role", String.class);
        return "ADMIN".equals(role) || "SUPER_ADMIN".equals(role);
    }

    private String getJwtFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Mono<Void> onError(org.springframework.web.server.ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        
        String errorResponse = String.format("{\"error\": \"%s\", \"status\": %d, \"timestamp\": \"%s\"}", 
                                           err, httpStatus.value(), java.time.Instant.now());
        
        return exchange.getResponse().writeWith(
            Mono.just(exchange.getResponse().bufferFactory().wrap(errorResponse.getBytes()))
        );
    }

    public static class Config {
        // Configuration properties for the filter
        private List<String> excludePaths;
        
        public List<String> getExcludePaths() {
            return excludePaths;
        }
        
        public void setExcludePaths(List<String> excludePaths) {
            this.excludePaths = excludePaths;
        }
    }
}
