package com.proyecto.panaderia.controller.admin;

import com.proyecto.panaderia.request.CategoriaRequest;
import com.proyecto.panaderia.services.CategoriaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/categorias")
public class AdminCategoriasController {
    private final CategoriaServicio categoriaServicio;

    @PostMapping
    public ResponseEntity<?> saveCategoria(@RequestBody CategoriaRequest categoriaRequest){
        categoriaServicio.saveCategoria(categoriaRequest);
        return ResponseEntity.ok(Map.of("message","Categoria guardada correctamente"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoria(@RequestBody CategoriaRequest categoriaRequest,@PathVariable Integer id){
            categoriaServicio.updateCategoria(categoriaRequest,id);
            return ResponseEntity.ok(Map.of("message","Categoria actualizada correctamente"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable Integer id){
        categoriaServicio.deleteCategoria(id);
        return ResponseEntity.ok(Map.of("message","Categoria eliminada correctamente"));
    }
}