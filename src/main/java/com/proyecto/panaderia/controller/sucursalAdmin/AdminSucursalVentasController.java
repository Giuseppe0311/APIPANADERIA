package com.proyecto.panaderia.controller.sucursalAdmin;


import com.proyecto.panaderia.services.VentasServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/adminsucursal/ventas")
@RequiredArgsConstructor
public class AdminSucursalVentasController {
    private final VentasServicio ventasServicio;

    @GetMapping
    public ResponseEntity<?> listarVentas(
            @RequestParam(required = false) Integer idSucursal,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Integer idVenta,
            @RequestParam(required = false) Integer productosMasVendidosPorSucursal,
            @RequestParam(required = false) Integer idSucursalVentasPorTipoPago
    ){
        if(idSucursal!=null && estado!=null){
            return ResponseEntity.ok(ventasServicio.listarVentas(idSucursal,estado));
        } else if (idVenta!=null) {
            return ResponseEntity.ok(ventasServicio.listarVentaPorId(idVenta));
        } else if (productosMasVendidosPorSucursal !=null) {
            return ResponseEntity.ok(ventasServicio.productosMasVendidos(productosMasVendidosPorSucursal));
        } else if ( idSucursalVentasPorTipoPago != null) {
            return ResponseEntity.ok(ventasServicio.ventasPorTipoPago(idSucursalVentasPorTipoPago));

        }
        return ResponseEntity.badRequest().body(Map.of("message","Petición incorrecta"));
    }
    @PutMapping("/{idVentaConcretada}")
    public ResponseEntity<?> cambiarEstadoVenta(@PathVariable Integer idVentaConcretada){
        ventasServicio.editarVenta(idVentaConcretada);
        return ResponseEntity.ok().body(Map.of("message","Estado de venta actualizado correctamente"));
    }

    @DeleteMapping("/{idVenta}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Integer idVenta){
        ventasServicio.eliminarVenta(idVenta);
        return ResponseEntity.ok().body(Map.of("message","Venta eliminada correctamente"));
    }
}
