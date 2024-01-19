package com.proyecto.panaderia.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.proyecto.panaderia.services.JWTService;
import com.proyecto.panaderia.services.impl.UserDetailLoaderImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Collection;

@RequiredArgsConstructor
@Component
public class JWTAuthorizacionFilter extends OncePerRequestFilter {
    // Inyección de dependencias de los servicios necesarios.
    private final JWTService jwtService;
    private final UserDetailLoaderImpl userDetailLoader;
    // Método para filtrar cada solicitud HTTP.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtiene el encabezado de autorización de la solicitud.
        String header = request.getHeader("Authorization");
        // Verifica si el encabezado es nulo o no comienza con "Bearer".
        if(header == null || !header.startsWith("Bearer")){
            // Continúa con la cadena de filtros sin hacer nada.
            filterChain.doFilter(request,response);
            return;
        }
        // Extrae el token JWT del encabezado.
        String token = header.substring(7);
        try {
            // Obtiene el nombre del usuario (subject) del token.
            String usuario = jwtService.nombredelSubject(token);
            // Verifica si el usuario no es nulo y no hay una autenticación actual.
            if(usuario !=null && SecurityContextHolder.getContext().getAuthentication() == null){
                // Carga los detalles del usuario.
                UserDetails userDetails = userDetailLoader.loadUserByUsername(usuario);
                // Verifica la validez del token.
                jwtService.verificarToken(token,userDetails);
                // Obtiene las autoridades del usuario a partir del token.
                Collection<? extends GrantedAuthority> authorities = jwtService.getAuthorities(token);
                // Crea un token de autenticación para el usuario.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        authorities
                );
                // Establece detalles adicionales de la autenticación.
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Guarda la autenticación en el contexto de seguridad.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            // Continúa con el procesamiento de la solicitud.
            filterChain.doFilter(request,response);
        } catch (ParseException | NoSuchAlgorithmException | InvalidKeySpecException | JOSEException e) {
            // En caso de una excepción, lanza una RuntimeException.
            throw new RuntimeException(e);
        }
    }
}
