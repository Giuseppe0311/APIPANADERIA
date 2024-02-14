package com.proyecto.panaderia.controller.admin;


import com.proyecto.panaderia.request.ProductosRequest;
import com.proyecto.panaderia.services.ProductosServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/productos")
@RequiredArgsConstructor
public class AdminProductosController {
    private final ProductosServicio productosServicio;
    @PostMapping
    public ResponseEntity<?> saveProducto(@ModelAttribute ProductosRequest productosRequest){
       productosServicio.saveProducto(productosRequest);
         return ResponseEntity.ok().body(Map.of("message","Producto guardado correctamente"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable Integer id, @ModelAttribute ProductosRequest productosRequest){
        productosServicio.updateProducto(productosRequest,id);
        return ResponseEntity.ok().body(Map.of("message","Producto actualizado correctamente"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Integer id){
        productosServicio.deleteProductoById(id);
        return ResponseEntity.ok().body(Map.of("message","Producto eliminado correctamente"));
    }
}
