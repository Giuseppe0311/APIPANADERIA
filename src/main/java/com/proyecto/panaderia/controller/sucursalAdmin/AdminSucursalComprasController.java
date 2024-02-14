package com.proyecto.panaderia.controller.sucursalAdmin;

import com.proyecto.panaderia.entity.Compras;
import com.proyecto.panaderia.request.ComprasRequest;
import com.proyecto.panaderia.services.ComprasServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adminsucursal/compras")
public class AdminSucursalComprasController {
    private final ComprasServicio comprasServicio;

    @GetMapping
    public ResponseEntity<?> listarCompras(
            @RequestParam(required = false) Integer idSucursal,
            @RequestParam(required = false) Integer idCompra
    ) {
       if (idSucursal != null) {
           return ResponseEntity.ok(comprasServicio.listarComprasPorSucursal(idSucursal));
       } else if (idCompra != null) {
              return ResponseEntity.ok(comprasServicio.getCompraPorId(idCompra));
       }
        return ResponseEntity.badRequest().body(Map.of("message", "No se encontro la sucursal"));
    }
    @PostMapping
    public ResponseEntity<?> guardarCompra(@RequestBody ComprasRequest comprasRequest) {
        comprasServicio.guardarCompra(comprasRequest);
        return ResponseEntity.ok(Map.of("message", "Compra guardada"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCompra(@PathVariable Integer id,@RequestBody String estado_pago) {
        comprasServicio.actualizaEstadoDeCompra(estado_pago, id);
        return ResponseEntity.ok(Map.of("message", "Compra actualizada"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCompra(@PathVariable Integer id) {
        comprasServicio.eliminarCompra(id);
        return ResponseEntity.ok(Map.of("message", "Compra eliminada"));
    }
}
