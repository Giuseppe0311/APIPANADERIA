package com.proyecto.panaderia.services;

import com.proyecto.panaderia.dto.ProductoSucursalDTO;
import com.proyecto.panaderia.entity.ProductoSucursal;
import com.proyecto.panaderia.request.ProductoSucursalRequest;

import java.util.List;
import java.util.Optional;

public interface ProductoSucursalServicio {
    List<ProductoSucursalDTO> getProductosSucursal();
    Optional<ProductoSucursalDTO> getProductosSucursalById(Integer id);
    List<ProductoSucursalDTO> getProductoSucursalById(Integer id);
    Optional<ProductoSucursalDTO> getProductolBySucursalId(Integer idproducto,Integer idsucursal);
    List<ProductoSucursalDTO> getProductosByCategoriaandSucursal(Integer idcategoria,Integer sucursalId);
    void saveProductoSucursal(ProductoSucursalRequest productoSucursalRequest);
    void updateProductoSucursal(ProductoSucursalRequest productoSucursalRequest,Integer id);
    void deleteProductoSucursalById(Integer id);
}
