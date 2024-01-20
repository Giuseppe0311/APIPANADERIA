package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.dto.ProductosDTO;
import com.proyecto.panaderia.entity.Productos;
import com.proyecto.panaderia.exceptions.ProductoNotFoundException;
import com.proyecto.panaderia.mapper.ProductosDTOMapper;
import com.proyecto.panaderia.repository.ProductosRepositorio;
import com.proyecto.panaderia.services.ProductosServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductosServicioImpl implements ProductosServicio {
    private final ProductosRepositorio productosRepositorio;
    private final ProductosDTOMapper productosDTOMapper;
    @Override
    public List<ProductosDTO> getProductos() {
        return productosRepositorio.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(productosDTOMapper)
                .toList();

    }

    @Override
    public Optional<ProductosDTO> getProductoById(Integer id) {
        return productosRepositorio.findById(id)
                .map(productosDTOMapper)
                .map(Optional::of)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
    }

    @Override
    public void saveProducto(Productos productos) {


    }

    @Override
    public void deleteProductoById(Integer id) {

    }
}
