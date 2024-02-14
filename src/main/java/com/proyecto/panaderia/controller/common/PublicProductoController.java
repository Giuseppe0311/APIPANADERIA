package com.proyecto.panaderia.controller.common;

import com.proyecto.panaderia.services.ProductoSucursalServicio;
import com.proyecto.panaderia.services.ProductosServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/productos")
public class PublicProductoController {
    private final ProductoSucursalServicio productoSucursalServicio;
    private final ProductosServicio productosServicio;
    //METODOS PROPIOS DEL PRODUCTO
    @GetMapping()
    public ResponseEntity<?> getProductos(
            @RequestParam(required = false) Integer idproducto,
            @RequestParam(required = false) Integer idsucursal,
            @RequestParam(required = false) Integer idcategoria,
            @RequestParam(required = false) Integer idempresa,
            @RequestParam(required = false) Integer idProductoSucursal
    ){
        if (idproducto != null) {
            return ResponseEntity.ok(idsucursal != null
                    ? productoSucursalServicio.getProductolBySucursalId(idproducto, idsucursal)
                    : productosServicio.getProductoById(idproducto));
        } else if (idcategoria != null && idsucursal != null) {
            return ResponseEntity.ok(productoSucursalServicio.getProductosByCategoriaandSucursal(idcategoria, idsucursal));
        } else if (idsucursal != null) {
            return ResponseEntity.ok(productoSucursalServicio.getProductoSucursalById(idsucursal));
        }else  if (idempresa != null) {
            return ResponseEntity.ok(productosServicio.getProductosByEmpresa(idempresa));
        } else if (idProductoSucursal != null) {
            return ResponseEntity.ok(productoSucursalServicio.getProductosSucursalById(idProductoSucursal));
        }
        return ResponseEntity.ok(productosServicio.getProductos());
    }

}
