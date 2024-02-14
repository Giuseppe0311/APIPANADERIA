package com.proyecto.panaderia.controller.common;

import com.proyecto.panaderia.services.SucursalServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/sucursales")
@RequiredArgsConstructor
public class PublicSucursalesControlller {
    private final SucursalServicio sucursalServicio;
    @GetMapping
    public ResponseEntity<?> getSucursales(
            @RequestParam(required = false) Integer idempresa,
            @RequestParam(required = false) Integer idsucursal
    ){
        if(idempresa!=null){
            return ResponseEntity.ok(sucursalServicio.getSucursalesByEmpresa(idempresa));
        }
        if(idsucursal!=null){
            return ResponseEntity.ok(sucursalServicio.getSucursal(idsucursal));
        }
        return ResponseEntity.ok(sucursalServicio.getSucursales());
    }
}
