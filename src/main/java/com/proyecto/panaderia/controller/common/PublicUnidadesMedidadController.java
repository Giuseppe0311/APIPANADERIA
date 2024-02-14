package com.proyecto.panaderia.controller.common;

import com.proyecto.panaderia.services.UnidadMedidaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/unidadesmedida")
public class PublicUnidadesMedidadController {
    private final UnidadMedidaServicio unidadMedidaServicio;

    @GetMapping
    public ResponseEntity<?> getUnidadesMedida(@RequestParam(required = false) Integer idempresa, @RequestParam(required = false) Integer idunidad) {
        if (idempresa != null) {
            return ResponseEntity.ok(unidadMedidaServicio.getUnidadesMedidaByEmpesa(idempresa));
        }
        if (idunidad != null) {
            return ResponseEntity.ok(unidadMedidaServicio.getUnidadMedidaById(idunidad));
        }
        return ResponseEntity.ok(unidadMedidaServicio.getUnidadesMedida());
    }

}
