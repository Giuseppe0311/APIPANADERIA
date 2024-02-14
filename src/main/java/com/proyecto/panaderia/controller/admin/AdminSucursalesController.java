package com.proyecto.panaderia.controller.admin;

import com.proyecto.panaderia.request.SucursalRequest;
import com.proyecto.panaderia.services.SucursalServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/sucursales")
@RequiredArgsConstructor
public class AdminSucursalesController {
    private final SucursalServicio sucursalServicio;
    @PostMapping
    public ResponseEntity<?> saveSucursales(@RequestBody SucursalRequest sucursalRequest){
        sucursalServicio.saveSucursal(sucursalRequest);
        return ResponseEntity.ok().body(Map.of("message","Sucursal guardada correctamente"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSucursales(@RequestBody SucursalRequest sucursalRequest,@PathVariable Integer id){
        sucursalServicio.updateSucursal(sucursalRequest, id);
        return ResponseEntity.ok().body(Map.of("message","Sucursal actualizada correctamente"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSucursales(@PathVariable Integer id){
        sucursalServicio.deleteSucursal(id);
        return ResponseEntity.ok().body(Map.of("message","Sucursal eliminada correctamente"));
    }
}
