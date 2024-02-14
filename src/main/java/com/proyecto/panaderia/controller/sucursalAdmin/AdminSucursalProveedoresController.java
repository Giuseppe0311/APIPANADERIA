package com.proyecto.panaderia.controller.sucursalAdmin;


import com.proyecto.panaderia.request.ProveedoresRequest;
import com.proyecto.panaderia.services.ProveedoresServicio;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/adminsucursal/proveedores")
@RequiredArgsConstructor
public class AdminSucursalProveedoresController {
    private final ProveedoresServicio proveedoresServicio;

    @GetMapping
    public ResponseEntity<?> listarProveedores(
            @RequestParam(required = false) Integer idProveedor,
            @RequestParam(required = false) Integer idSucursal
    ) {
        if (idSucursal != null) {
            return ResponseEntity.ok(proveedoresServicio.getProveedoresBySucursal(idSucursal));
        } else if (idProveedor != null) {
            return ResponseEntity.ok(proveedoresServicio.getProveedorById(idProveedor));
        }
        return ResponseEntity.badRequest().body("Petición incorrecta");

    }

    @PostMapping
    public ResponseEntity<?> saveProveedor(@RequestBody ProveedoresRequest proveedoresRequest){
        proveedoresServicio.saveProveedor(proveedoresRequest);
        return ResponseEntity.ok().body(Map.of("message","Proveedor guardado correctamente"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProveedor(@PathVariable Integer id,@RequestBody ProveedoresRequest proveedoresRequest){
        proveedoresServicio.updateProveedor(id,proveedoresRequest);
        return ResponseEntity.ok().body(Map.of("message","Proveedor actualizado correctamente"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProveedor(@PathVariable Integer id){
        proveedoresServicio.deleteProveedor(id);
        return ResponseEntity.ok().body(Map.of("message","Proveedor eliminado correctamente"));
    }
}

