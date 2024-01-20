package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.dto.ProductoSucursalDTO;
import com.proyecto.panaderia.dto.SucursalDTO;
import com.proyecto.panaderia.entity.ProductoSucursal;
import com.proyecto.panaderia.entity.Sucursales;
import com.proyecto.panaderia.exceptions.ProductoNotFoundException;
import com.proyecto.panaderia.mapper.ProductoSucursalDTOMapper;
import com.proyecto.panaderia.repository.ProductoSucursalRepositorio;
import com.proyecto.panaderia.repository.ProductosRepositorio;
import com.proyecto.panaderia.repository.SucursalesRepositorio;
import com.proyecto.panaderia.services.ProductoSucursalServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoSucursalServicioImpl implements ProductoSucursalServicio {
    private final ProductoSucursalDTOMapper productoSucursalDTOMapper;
    private final ProductoSucursalRepositorio productoSucursalRepositorio;

    @Override
    public List<ProductoSucursalDTO> getProductosSucursal() {
        return productoSucursalRepositorio.findAll()
                .stream()
                .map(productoSucursalDTOMapper)
                .toList();
    }

    @Override
    public List<ProductoSucursalDTO> getProductoSucursalById(Integer id) {
        return productoSucursalRepositorio.findBySucursalesId(id)
                .stream()
                .map(productoSucursalDTOMapper)
                .toList();
    }

    @Override
    public void saveProductoSucursal(ProductoSucursal productoSucursal) {

    }

    @Override
    public void deleteProductoSucursalById(Integer id) {

    }
}
