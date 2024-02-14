package com.proyecto.panaderia.controller.superAdmin;

import com.proyecto.panaderia.request.EmpresaRequest;
import com.proyecto.panaderia.services.EmpresaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/superadmin/empresas")
public class SuperAdminEmpresasController {
    private final EmpresaServicio empresaServicio;
    @PostMapping
    public ResponseEntity<?> saveEmpresa(@ModelAttribute EmpresaRequest empresaRequest){
        empresaServicio.saveEmpresa(empresaRequest);
        return ResponseEntity.ok(Map.of("mensaje","Empresa guardada correctamente"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmpresa(@ModelAttribute EmpresaRequest empresaRequest,@PathVariable Integer id){
        empresaServicio.updateEmpresa(empresaRequest,id);
        return ResponseEntity.ok(Map.of("mensaje","Empresa actualizada correctamente"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmpresa(@PathVariable Integer id){
        empresaServicio.deleteEmpresaById(id);
        return ResponseEntity.ok(Map.of("mensaje","Empresa eliminada correctamente"));
    }
}
