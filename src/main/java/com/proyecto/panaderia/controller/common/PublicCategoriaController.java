package com.proyecto.panaderia.controller.common;

import com.proyecto.panaderia.services.CategoriaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/categorias")
@RequiredArgsConstructor
public class PublicCategoriaController {
    private final CategoriaServicio categoriaServicio;
    @GetMapping()
    public ResponseEntity<?> getCategorias(
            @RequestParam(required = false) Integer idcategoria,
            @RequestParam(required = false) Integer idempresa
    ){
        if(idcategoria!=null){
            return ResponseEntity.ok(categoriaServicio.getCategoriaById(idcategoria));
        }
        if(idempresa!=null){
            return ResponseEntity.ok(categoriaServicio.getCategoriasbyIdEmpresa(idempresa));
        }
        return ResponseEntity.badRequest().body("petición incorrecta");
    }

}
