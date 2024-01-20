package com.proyecto.panaderia.services;

import com.proyecto.panaderia.dto.ProductoSucursalDTO;
import com.proyecto.panaderia.entity.ProductoSucursal;

import java.util.List;
import java.util.Optional;

public interface ProductoSucursalServicio {
    List<ProductoSucursalDTO> getProductosSucursal();
    List<ProductoSucursalDTO> getProductoSucursalById(Integer id);
    void saveProductoSucursal(ProductoSucursal productoSucursal);
    void deleteProductoSucursalById(Integer id);
}
