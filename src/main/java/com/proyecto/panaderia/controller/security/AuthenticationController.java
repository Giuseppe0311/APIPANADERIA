package com.proyecto.panaderia.controller.security;


import com.proyecto.panaderia.dto.JWTResponseDto;
import com.proyecto.panaderia.request.LoginRequest;
import com.proyecto.panaderia.request.RegistroRequest;
import com.proyecto.panaderia.security.ServicioAuthenticacion;
import com.proyecto.panaderia.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final ServicioAuthenticacion servicioAuthenticacion;

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody LoginRequest loginRequest) {
        JWTResponseDto response = servicioAuthenticacion.login(loginRequest);
        return ResponseEntity.status(response.status()).body(response);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroRequest registroRequest) {
        servicioAuthenticacion.registrar(registroRequest);
        return ResponseEntity.ok().body(Map.of("message", "Usuario registrado correctamente"));
    }

}
