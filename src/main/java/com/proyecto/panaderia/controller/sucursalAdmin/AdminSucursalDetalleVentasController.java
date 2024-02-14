package com.proyecto.panaderia.controller.sucursalAdmin;

import com.proyecto.panaderia.services.DetalleVentasServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/adminsucursal/detalleventas")
@RequiredArgsConstructor
public class AdminSucursalDetalleVentasController {
    private final DetalleVentasServicio detalleVentasServicio;
    @GetMapping
    public ResponseEntity<?> getDetalleVentas(
            @RequestParam Integer idVenta
    ){
        if (idVenta!=null){
            return ResponseEntity.ok(detalleVentasServicio.getDetalleVentas(idVenta));
        }
        return ResponseEntity.badRequest().body(Map.of("message","Petición incorrecta"));
    }
}
