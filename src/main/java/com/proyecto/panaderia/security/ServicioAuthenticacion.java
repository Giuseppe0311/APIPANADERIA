package com.proyecto.panaderia.security;

import com.nimbusds.jose.JOSEException;
import com.proyecto.panaderia.dto.JWTResponseDto;
import com.proyecto.panaderia.entity.Empresas;
import com.proyecto.panaderia.entity.Perfiles;
import com.proyecto.panaderia.entity.Sucursales;
import com.proyecto.panaderia.entity.Usuarios;
import com.proyecto.panaderia.exceptions.*;
import com.proyecto.panaderia.repository.EmpresaRepositorio;
import com.proyecto.panaderia.repository.PerfilRepository;
import com.proyecto.panaderia.repository.SucursalesRepositorio;
import com.proyecto.panaderia.repository.UsuarioRepositorio;
import com.proyecto.panaderia.request.LoginRequest;
import com.proyecto.panaderia.request.RegistroRequest;
import com.proyecto.panaderia.services.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final EmpresaRepositorio empresaRepository;
    private  final SucursalesRepositorio sucursalRepository;
    // Método para registrar un nuevo usuario.
    @Transactional
    public void registrar(RegistroRequest registroRequest) {
        String usuarioNormalizado = registroRequest.getUsuario().toLowerCase().trim();
        String dniNormalizado = registroRequest.getDni().toLowerCase().trim();
        String correoNormalizado = registroRequest.getCorreo().toLowerCase().trim();
        usuarioRepositorio.findByUsuarioOrDniOrCorreoIgnoreCaseAndTrim(usuarioNormalizado, dniNormalizado, correoNormalizado)
                .ifPresent(usuario -> {
                    throw new UsuarioExistException("El usuario, DNI o correo ya se encuentra registrado");
                });
        /*
        * INCLUIRLO AQUI
         */
        // Encripta la contraseña del usuario.
        String contrasenaEncriptada =passwordEncoder.encode(registroRequest.getContrasena());
        // Conjunto para almacenar los perfiles del usuario.



        // Crea una nueva entidad de usuario y establece sus propiedades.
        Usuarios usuarios = new Usuarios();
        usuarios.setUsuario(registroRequest.getUsuario());
        usuarios.setContrasena(contrasenaEncriptada);
        usuarios.setNombre(registroRequest.getNombre());
        usuarios.setCorreo(registroRequest.getCorreo());
        usuarios.setTelefono(registroRequest.getTelefono());
        usuarios.setDni(registroRequest.getDni());
        usuarios.setEstado(true);

        Set<Perfiles> authorities = new HashSet<>();
        if (registroRequest.getIdempresa() != null && registroRequest.getIdsucursal() == null) {
            Empresas empresa = empresaRepository.findById(registroRequest.getIdempresa())
                    .orElseThrow(() -> new EmpresaNotFoundException("Empresa no encontrada"));
            usuarios.setEmpresa(empresa);
            // Itera sobre los ID de perfiles y los busca en el repositorio.
            Perfiles perfilAdmin = perfilRepository.findByNombre("ADMIN")
                    .orElseThrow(() -> new PerfilNotFoundException("Perfil 'ADMIN' no encontrado"));
            authorities.add(perfilAdmin);
        }

        if (registroRequest.getIdsucursal() != null && registroRequest.getIdempresa() != null) {
            Sucursales sucursal = sucursalRepository.findById(registroRequest.getIdsucursal())
                    .orElseThrow(() -> new SucursalNotFoundException("Sucursal no encontrada"));
            usuarios.setSucursal(sucursal);
            Empresas empresa = empresaRepository.findById(registroRequest.getIdempresa())
                    .orElseThrow(() -> new EmpresaNotFoundException("Empresa no encontrada"));
            usuarios.setEmpresa(empresa);
            // Itera sobre los ID de perfiles y los busca en el repositorio.
            Perfiles perfilAdminSucursal = perfilRepository.findByNombre("ADMINSUCURSAL")
                    .orElseThrow(() -> new PerfilNotFoundException("Perfil 'ADMINSUCURSAL' no encontrado"));
            authorities.add(perfilAdminSucursal);
        }
        if (authorities.isEmpty()) {
            Perfiles perfilUser = perfilRepository.findByNombre("USER")
                    .orElseThrow(() -> new PerfilNotFoundException("Perfil 'USER' no encontrado"));
            authorities.add(perfilUser);
        }
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
            Integer idEmpresa = user.getEmpresa() != null ? user.getEmpresa().getId() : null;
            Integer idSucursal = user.getSucursal() != null ? user.getSucursal().getId() : null;
            return new JWTResponseDto(jwtService.generarJWT(userDetails.getUsername(),
                    userDetails.getAuthorities(),
                    user.getId(),
                    idEmpresa,
                    idSucursal),"Login exitoso", HttpStatus.ACCEPTED);
        } catch (BadCredentialsException e) {
            // Maneja la excepción de credenciales incorrectas.
            return new JWTResponseDto(null,"Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        } catch (UsuarioNotFoundException e){
            // Maneja la excepción cuando el usuario no se encuentra.
            return new JWTResponseDto(null,"Usuario no encontrado", HttpStatus.NOT_FOUND);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | JOSEException e) {
            // Maneja otras excepciones, como errores al generar el token.
            return new JWTResponseDto(null,"Error al generar el token : "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
