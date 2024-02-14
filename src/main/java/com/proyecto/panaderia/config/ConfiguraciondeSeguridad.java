package com.proyecto.panaderia.config;

import com.proyecto.panaderia.security.JWTAuthorizacionFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfiguraciondeSeguridad {
    // Inyección de un filtro de autorización JWT.
    public final JWTAuthorizacionFilter jwtAuthorizacionFilter;
    // Bean para codificar contraseñas usando BCrypt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // Bean para el administrador de autenticación.
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        // Se crea un proveedor de autenticación basado en DAO.
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        // Se establece el servicio de detalles de usuario y el codificador de contraseñas.
        daoProvider.setUserDetailsService(userDetailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        // Devuelve un administrador de autenticación que usa el proveedor de autenticación.
        return new ProviderManager(daoProvider);
    }
    //configura CORS para la ruta http://localhost:4200

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "Content-Type", "x-auth-token"));
        configuration.setExposedHeaders(List.of("Content-Disposition"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Bean para la cadena de filtros de seguridad.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Se deshabilita la protección CSRF.
                .csrf((csrf) -> csrf.disable())
                // Se configura la autorización de las peticiones.
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/docs", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/webjars/**").permitAll();
                    // Se permiten las peticiones de autenticación.
                    auth.requestMatchers("/api/auth/**").permitAll();
                    auth.requestMatchers("/api/public/**").permitAll();
                    auth.requestMatchers("/api/user/**").hasAnyRole("USER","ADMIN","SUPERADMIN");
                    auth.requestMatchers("/api/admin/**").hasAnyRole("ADMIN","SUPERADMIN");
                    auth.requestMatchers("api/adminsucursal/**").hasAnyRole("ADMINSUCURSAL","ADMIN","SUPERADMIN");
                    auth.requestMatchers("/api/superadmin/**").hasRole("SUPERADMIN");
                    // Todas las demás solicitudes requieren autenticación.
                    auth.anyRequest().authenticated();
                });
        // Establece la política de creación de sesiones como STATELESS. el cual no se crea ninguna sesión.
        // de lo contrario, Spring Security creará una sesión para cada solicitud atravez de un token.
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // Se establece el administrador de autenticación.
        http.authenticationManager(authenticationManager);
        // Se agrega el filtro de autorización JWT antes del filtro de autenticación de nombre de usuario y contraseña.
        http.addFilterBefore(jwtAuthorizacionFilter, UsernamePasswordAuthenticationFilter.class);
        // Se configura el manejador de excepciones para que devuelva 401.
        http.exceptionHandling(execptionHandling -> execptionHandling
                .authenticationEntryPoint((request, response, authenticationException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"UNAUTHORIZED"))
        );
        return http.build();
    }


}
