package com.proyecto.panaderia.services;

import com.proyecto.panaderia.dto.ProductosDTO;
import com.proyecto.panaderia.entity.Productos;
import com.proyecto.panaderia.request.ProductosRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductosServicio {
    List<ProductosDTO> getProductos();
    Optional<ProductosDTO> getProductoById(Integer id);
    List<ProductosDTO> getProductosByEmpresa(Integer idempresa);
    void saveProducto(ProductosRequest productosRequest);
    void updateProducto(ProductosRequest productosRequest,Integer id);
    void deleteProductoById(Integer id);
}
