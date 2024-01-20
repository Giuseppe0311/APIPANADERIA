package com.proyecto.panaderia.controller;

import com.proyecto.panaderia.services.SucursalServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
public class SucursalesControlller {
    private final SucursalServicio sucursalServicio;
    @GetMapping
    public ResponseEntity<?> getSucursales(){
        return ResponseEntity.ok(sucursalServicio.getSucursales());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSucursal(@PathVariable Integer id){
        return ResponseEntity.ok(sucursalServicio.getSucursal(id));
    }
    @GetMapping("/empresa/{id}")
    public ResponseEntity<?> getSucursalesByEmpresa(@PathVariable Integer id){
        return ResponseEntity.ok(sucursalServicio.getSucursalesByEmpresa(id));
    }

}
