package com.proyecto.panaderia.controller;

import com.proyecto.panaderia.dto.ProductosDTO;
import com.proyecto.panaderia.services.ProductoSucursalServicio;
import com.proyecto.panaderia.services.ProductosServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoSucursalServicio productoSucursalServicio;
    private final ProductosServicio productosServicio;
    //METODOS PROPIOS DEL PRODUCTO
    @GetMapping()
    public ResponseEntity<List<ProductosDTO>> getProductos(){
        return ResponseEntity.ok(productosServicio.getProductos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductosDTO>> getProductoById(@PathVariable Integer id){
        return ResponseEntity.ok().body(productosServicio.getProductoById(id));
    }

    ///METODOs productos  - sucursal
    @GetMapping("/sucursales")
    public ResponseEntity<?> getProductosSucursal(){
        return ResponseEntity.ok(productoSucursalServicio.getProductosSucursal());
    }
    @GetMapping("/sucursales/{id}")
    public ResponseEntity<?> getProductoSucursalById(@PathVariable Integer id){
        return ResponseEntity.ok(productoSucursalServicio.getProductoSucursalById(id));
    }


}
