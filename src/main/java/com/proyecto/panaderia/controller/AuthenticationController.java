package com.proyecto.panaderia.controller;


import com.proyecto.panaderia.request.LoginRequest;
import com.proyecto.panaderia.request.RegistroRequest;
import com.proyecto.panaderia.security.ServicioAuthenticacion;
import com.proyecto.panaderia.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final ServicioAuthenticacion servicioAuthenticacion;

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(servicioAuthenticacion.login(loginRequest));
    }
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroRequest registroRequest) {
        servicioAuthenticacion.registrar(registroRequest);
        return ResponseEntity.ok().build();
    }

}
