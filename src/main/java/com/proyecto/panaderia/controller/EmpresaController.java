package com.proyecto.panaderia.controller;

import com.proyecto.panaderia.services.EmpresaServicio;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/empresa")
public class EmpresaController {
    private final EmpresaServicio empresaServicio;
    @GetMapping()
    public ResponseEntity<?> getEmpresas(){
        return ResponseEntity.ok(empresaServicio.getEmpresas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmpresaById(@PathVariable Integer id){
        return ResponseEntity.ok(empresaServicio.getEmpresaById(id));
    }
}
