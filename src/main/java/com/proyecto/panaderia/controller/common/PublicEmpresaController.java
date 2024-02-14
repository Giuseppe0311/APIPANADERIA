package com.proyecto.panaderia.controller.common;

import com.proyecto.panaderia.services.EmpresaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/empresas")
public class PublicEmpresaController {
    private final EmpresaServicio empresaServicio;
    @GetMapping()
    public ResponseEntity<?> getEmpresas(
            @RequestParam(required = false) Integer idempresa
    ){
        if(idempresa!=null){
            return ResponseEntity.ok(empresaServicio.getEmpresaById(idempresa));
        }
        return ResponseEntity.ok(empresaServicio.getEmpresas());
    }
}
