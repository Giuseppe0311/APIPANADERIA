package com.proyecto.panaderia.services;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;

public interface JWTService {
    String generarJWT(String usuarios,Collection<? extends GrantedAuthority> roles,
                      Integer idusuario,Integer idempresa,Integer idsucursal) throws IOException
            , NoSuchAlgorithmException, InvalidKeySpecException, JOSEException;

    JWTClaimsSet verificarToken(String token, UserDetails userDetails) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException;
    String nombredelSubject(String token) throws ParseException;
    Collection<? extends GrantedAuthority> getAuthorities(String token) throws ParseException;

    }

