package com.proyecto.panaderia.services;

import com.proyecto.panaderia.dto.ProductosDTO;
import com.proyecto.panaderia.entity.Productos;

import java.util.List;
import java.util.Optional;

public interface ProductosServicio {
    List<ProductosDTO> getProductos();
    Optional<ProductosDTO> getProductoById(Integer id);
    void saveProducto(Productos productos);
    void deleteProductoById(Integer id);
}
