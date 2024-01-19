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
    // Inyección de dependencias de varios componentes y servicios.
    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final PerfilRepository perfilRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    // Método para registrar un nuevo usuario.
    public void registrar(RegistroRequest registroRequest) {
        // TODO: 18/01/2024 No olvidar que falta validad la existencia del usuario y el correo
        /*
        * INCLUIRLO AQUI
         */
        // Encripta la contraseña del usuario.
        String contrasenaEncriptada =passwordEncoder.encode(registroRequest.getContrasena());
        // Conjunto para almacenar los perfiles del usuario.
        Set<Perfiles> authorities = new HashSet<>();
        // Itera sobre los ID de perfiles y los busca en el repositorio.
        for (Integer perfilid : registroRequest.getPerfiles()) {
            Perfiles perfil = perfilRepository.findById(perfilid).orElseThrow(() ->
                    new PerfilNotFoundException("No se encontro uno o varios perfiles asignados"));
            authorities.add(perfil);
        }
        // Crea una nueva entidad de usuario y establece sus propiedades.
        Usuarios usuarios = new Usuarios();
        usuarios.setUsuario(registroRequest.getUsuario());
        usuarios.setContrasena(contrasenaEncriptada);
        usuarios.setNombre(registroRequest.getNombre());
        usuarios.setCorreo(registroRequest.getCorreo());
        usuarios.setTelefono(registroRequest.getTelefono());
        usuarios.setEstado(true);
        usuarios.setPerfiles(authorities);
        // Guarda el usuario en el repositorio.
        usuarioRepositorio.save(usuarios);
    }
    // Método para autenticar a un usuario y generar un token JWT.
    public JWTResponseDto login(LoginRequest loginRequest){
        try {
            // Autentica al usuario usando el administrador de autenticación.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsuario(), loginRequest.getContrasena()));
            // Obtiene los detalles del usuario autenticado.
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Busca el usuario en el repositorio.
            Usuarios user = usuarioRepositorio.findByUsuario(userDetails.getUsername()).orElseThrow(() ->
                    new UsuarioNotFoundException("Su usuario no se encuentra registrado"));
            // Genera un token JWT y crea una respuesta.
            return new JWTResponseDto(jwtService.generarJWT(userDetails.getUsername(), userDetails.getAuthorities(), user.getId()),"Login exitoso");
        }catch (UsuarioNotFoundException e){
            return new JWTResponseDto(null,"Usuario no encontrado");
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | JOSEException e) {
            return new JWTResponseDto(null,"Error al generar el token : "+e.getMessage());
        }
    }
}
