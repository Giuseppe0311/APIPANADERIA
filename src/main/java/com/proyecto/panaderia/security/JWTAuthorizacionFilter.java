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
    private final JWTService jwtService;
    private final UserDetailLoaderImpl userDetailLoader;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = header.substring(7);
        try {
            String usuario = jwtService.nombredelSubject(token);
            if(usuario !=null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailLoader.loadUserByUsername(usuario);
                jwtService.verificarToken(token,userDetails);
                Collection<? extends GrantedAuthority> authorities = jwtService.getAuthorities(token);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        authorities
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request,response);
        } catch (ParseException | NoSuchAlgorithmException | InvalidKeySpecException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
