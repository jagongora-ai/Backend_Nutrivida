package com.example.ms_agendar.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration 
@EnableMethodSecurity 
public class SecurityConfig {
 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())                                   // usa token, no cookies
                .cors(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/prestaciones").permitAll()
                        // la protege la clave interna (X-ClaveInterna), la llama ms-pagos
                        .requestMatchers(HttpMethod.PATCH, "/api/citas/*/pagada").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((req, res, ex) ->
                                escribirError(req, res, 401, "Debes iniciar sesión para continuar"))
                        .accessDeniedHandler((req, res, ex) ->
                                escribirError(req, res, 403, "No tienes permiso para realizar esta accion")))
                .addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
 
        return http.build();
    }
 
    @Bean
    public CorsConfigurationSource corsConfigurationSource(@Value("${cors.origen}") String origen) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(origen));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
 
    /** Misma forma que ErrorResponse: status, mensaje, fecha y ruta. */
    private static void escribirError(HttpServletRequest req, HttpServletResponse res, int status, String mensaje)
            throws IOException {
        res.setStatus(status);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json;charset=UTF-8");
        String ruta = req.getRequestURI().replace("\\", "\\\\").replace("\"", "\\\"");
        res.getWriter().write("{\"status\":" + status
                + ",\"mensaje\":\"" + mensaje
                + "\",\"fecha\":\"" + LocalDateTime.now()
                + "\",\"ruta\":\"" + ruta + "\"}");
    }
}
