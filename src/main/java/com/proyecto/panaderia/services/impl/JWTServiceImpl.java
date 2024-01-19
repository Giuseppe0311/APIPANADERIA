package com.proyecto.panaderia.services.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.proyecto.panaderia.services.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JWTServiceImpl implements JWTService {
    @Value("classpath:jwtKeys/private_key.pem")
    private Resource llavePrivada;
    @Value("classpath:jwtKeys/public_key.pem")
    private Resource llavePublica;

    @Override
    public String generarJWT(String usuario,Collection<? extends GrantedAuthority> roles,Integer idusuario) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        // Carga la llave privada desde el archivo.
        PrivateKey privateKey = cargarLlavePrivada(llavePrivada);
        // Crea un firmador JWT (JWSSigner) utilizando la llave privada RSA.
        JWSSigner signer = new RSASSASigner(privateKey);
        // Obtiene la fecha y hora actual.
        Date now = new Date();
        // Construye el conjunto de declaraciones (claims) para el JWT.
        // Establece el 'subject' (sujeto) del token como el ID del usuario convertido a String.
        // Establece la fecha de emisión ('issueTime') al momento actual.
        // Establece la fecha de expiración ('expirationTime') para 24 horas después del momento actual.
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(usuario)
                .claim("roles", roles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" ")))
                .claim("idusuario",idusuario)
                .issueTime(now)
                .expirationTime(new Date(now.getTime() + 1000 * 60 * 60 * 24))
                .build();
        // Crea un JWT que posteriormente sera firmado con el algoritmo RS256 y el conjunto de declaraciones creado anteriormente(claimSet).
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256),claimsSet);
        // Firma el JWT con el firmador.
        signedJWT.sign(signer);
        // Serializa el JWT firmado a un string y lo devuelve.
        return signedJWT.serialize();
    }
    @Override
    public JWTClaimsSet verificarToken(String token, UserDetails userDetails) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException {
       PublicKey publicKey = cargarLlavepublica(llavePublica);
       SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
        if( !signedJWT.verify(verifier)){
            throw new JOSEException("Firma de token no valida");
        }
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        if(claimsSet.getExpirationTime().before(new Date())){
            throw new JOSEException("Token expirado");

        }
        if(!claimsSet.getSubject().equals(userDetails.getUsername())){
            throw new JOSEException("Token no corresponde al usuario");
        }
        return claimsSet;
    }
    @Override
    public String nombredelSubject(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT.getJWTClaimsSet().getSubject();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

        // Suponiendo que los roles están guardados como una cadena separada por espacios
        // en el claim "roles"
        String rolesString = (String) claims.getClaim("roles");
        if (rolesString == null || rolesString.isEmpty()) {
            return Collections.emptyList();
        }

        String[] roles = rolesString.split("\\s+");
        return Arrays.stream(roles)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    /*
     * Carga una clave privada desde un recurso y la prepara para su uso en operaciones criptográficas.
     * El proceso incluye leer el archivo de la clave, eliminar encabezados y pies de la clave,
     * y decodificar la clave codificada en Base64 a su formato binario.
    */
    private PrivateKey cargarLlavePrivada(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        // Lee los bytes de la clave privada desde el archivo.
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        // Elimina los encabezados y pies del archivo de clave privada y cualquier espacio blanco.
        String llavePrivada = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----",
                        "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        // Decodifica la clave privada del formato Base64 a su forma binaria.
        byte[] llavedecodificada = Base64.getDecoder().decode(llavePrivada);
        // Crea una instancia de KeyFactory para el algoritmo RSA y genera la clave privada.
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(llavedecodificada));
    }
    private PublicKey cargarLlavepublica(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        // Lee los bytes de la clave privada desde el archivo.
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        // Elimina los encabezados y pies del archivo de clave privada y cualquier espacio blanco.
        String llavePublica = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        // Decodifica la clave privada del formato Base64 a su forma binaria.
        byte[] llavedecodificada = Base64.getDecoder().decode(llavePublica);
        // Crea una instancia de KeyFactory para el algoritmo RSA y genera la clave privada.
        KeyFactory keyFactory =KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(llavedecodificada));
    }
}
