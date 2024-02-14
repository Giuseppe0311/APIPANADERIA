package com.proyecto.panaderia.controller.sucursalAdmin;

import com.proyecto.panaderia.request.ProductoSucursalRequest;
import com.proyecto.panaderia.services.ProductoSucursalServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/adminsucursal/productosucursal")
@RequiredArgsConstructor
public class AdminSucursalProductoSucursalController {
    private  final ProductoSucursalServicio productoSucursalServicio;

    @PostMapping
    public ResponseEntity<?> saveProductoSucursal(@RequestBody ProductoSucursalRequest productoSucursalRequest){
        productoSucursalServicio.saveProductoSucursal(productoSucursalRequest);
        return ResponseEntity.ok().body(Map.of("message","Producto Por sucursal guardado correctamente"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductoSucursal(@PathVariable Integer id, @RequestBody ProductoSucursalRequest productoSucursalRequest){
        productoSucursalServicio.updateProductoSucursal(productoSucursalRequest,id);
        return ResponseEntity.ok().body(Map.of("message","Producto por Sucursal actualizado correctamente"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductoSucursal(@PathVariable Integer id){
        productoSucursalServicio.deleteProductoSucursalById(id);
        return ResponseEntity.ok().body(Map.of("message","Producto por Sucursal eliminado correctamente"));
    }
}
