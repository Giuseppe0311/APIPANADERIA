package com.proyecto.panaderia.controller.user;

import com.proyecto.panaderia.entity.Ventas;
import com.proyecto.panaderia.request.VentasRequest;
import com.proyecto.panaderia.services.VentasServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/ventas")
public class UserVentasController {
    private final VentasServicio ventasServicio;

    @PostMapping
    public ResponseEntity<?> registrarVenta(@RequestBody VentasRequest ventasRequest) {
        ventasServicio.registrarVenta(ventasRequest);
        return ResponseEntity.ok().body(Map.of("message", "Venta registrada correctamente"));
    }

}
