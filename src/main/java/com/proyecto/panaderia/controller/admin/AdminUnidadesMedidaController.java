package com.proyecto.panaderia.controller.admin;

import com.proyecto.panaderia.request.UnidadMedidaRequest;
import com.proyecto.panaderia.services.UnidadMedidaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/admin/unidadesmedida")
@RequiredArgsConstructor
@RestController
public class AdminUnidadesMedidaController {
    private final UnidadMedidaServicio unidadMedidaServicio;
    @PostMapping
    public ResponseEntity<?> saveUnidadMedida(@RequestBody UnidadMedidaRequest unidadMedidaRequest){
        unidadMedidaServicio.saveUnidadMedida(unidadMedidaRequest);
        return ResponseEntity.ok().body(Map.of("message","Unidad de medida guardada"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUnidadMedida(@PathVariable Integer id, @RequestBody UnidadMedidaRequest unidadMedidaRequest){
        unidadMedidaServicio.updateUnidadMedida(unidadMedidaRequest,id);
        return ResponseEntity.ok().body(Map.of("message","Unidad de medida actualizada"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUnidadMedida(@PathVariable Integer id){
        unidadMedidaServicio.deleteUnidadMedidaById(id);
        return ResponseEntity.ok().body(Map.of("message","Unidad de medida eliminada"));
    }
}
