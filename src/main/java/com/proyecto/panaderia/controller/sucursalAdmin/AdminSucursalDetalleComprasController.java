package com.proyecto.panaderia.controller.sucursalAdmin;

import com.proyecto.panaderia.dto.DetalleComprasDTO;
import com.proyecto.panaderia.dto.DetalleVentasDTO;
import com.proyecto.panaderia.mapper.DetalleComprasDTOMapper;
import com.proyecto.panaderia.mapper.DetalleVentasDTOMapper;
import com.proyecto.panaderia.repository.DetalleComprasRepositorio;
import com.proyecto.panaderia.repository.DetalleVentasRepositorio;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adminsucursal/detallecompras")
public class AdminSucursalDetalleComprasController {
    private final DetalleComprasRepositorio detalleComprasRepositorio;
    private  final DetalleComprasDTOMapper detalleComprasDTOMapper;
    @GetMapping()
    public ResponseEntity<?> listarDetalleComprasbyIdCompra(@RequestParam Integer idCompra){
        if (idCompra!=null){
            List<DetalleComprasDTO> detalleVentasDTO = detalleComprasRepositorio.getDetalleComprasByIdCompra(idCompra)
                    .stream()
                    .map(detalleComprasDTOMapper)
                    .toList();
            return ResponseEntity.ok(detalleVentasDTO);
        }
        return ResponseEntity.badRequest().body(Map.of("message","Petición incorrecta"));
    }
}
