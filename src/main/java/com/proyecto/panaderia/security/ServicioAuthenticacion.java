package com.proyecto.panaderia.security;

import com.nimbusds.jose.JOSEException;
import com.proyecto.panaderia.dto.JWTResponseDto;
import com.proyecto.panaderia.entity.Perfiles;
import com.proyecto.panaderia.entity.Usuarios;
import com.proyecto.panaderia.exceptions.PerfilNotFoundException;
import com.proyecto.panaderia.exceptions.UsuarioNotFoundException;
import com.proyecto.panaderia.repository.PerfilRepository;
import com.proyecto.panaderia.repository.UsuarioRepositorio;
import com.proyecto.panaderia.request.LoginRequest;
import com.proyecto.panaderia.request.RegistroRequest;
import com.proyecto.panaderia.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
// TODO : TENER EN CUENTA QUE AQUI SE ESTA HACIENDO PRUEBAS , CONSIDERAR MEJORAR PRACTICAS
public class ServicioAuthenticacion {
    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final PerfilRepository perfilRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public void registrar(RegistroRequest registroRequest) {
        // TODO: 18/01/2024 No olvidar que falta validad la existencia del usuario y el correo
        /*
        * INCLUIRLO AQUI
         */
       String contrasenaEncriptada =passwordEncoder.encode(registroRequest.getContrasena());
        Set<Perfiles> authorities = new HashSet<>();
        for (Integer perfilid : registroRequest.getPerfiles()) {
            Perfiles perfil = perfilRepository.findById(perfilid).orElseThrow(() ->
                    new PerfilNotFoundException("No se encontro uno o varios perfiles asignados"));
            authorities.add(perfil);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setUsuario(registroRequest.getUsuario());
        usuarios.setContrasena(contrasenaEncriptada);
        usuarios.setNombre(registroRequest.getNombre());
        usuarios.setCorreo(registroRequest.getCorreo());
        usuarios.setTelefono(registroRequest.getTelefono());
        usuarios.setEstado(true);
        usuarios.setPerfiles(authorities);
        usuarioRepositorio.save(usuarios);
    }
    public JWTResponseDto login(LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsuario(), loginRequest.getContrasena()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Usuarios user = usuarioRepositorio.findByUsuario(userDetails.getUsername()).orElseThrow(() ->
                    new UsuarioNotFoundException("Su usuario no se encuentra registrado"));
              return new JWTResponseDto(jwtService.generarJWT(userDetails.getUsername(), userDetails.getAuthorities(), user.getId()),"Login exitoso");

        }catch (UsuarioNotFoundException e){
            return new JWTResponseDto(null,"Usuario no encontrado");
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | JOSEException e) {
            return new JWTResponseDto(null,"Error al generar el token : "+e.getMessage());
        }
    }
}
