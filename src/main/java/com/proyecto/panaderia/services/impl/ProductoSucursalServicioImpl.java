package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.dto.ProductoSucursalDTO;
import com.proyecto.panaderia.entity.ProductoSucursal;
import com.proyecto.panaderia.exceptions.ProductoNotFoundException;
import com.proyecto.panaderia.exceptions.ProductoSucursalExistException;
import com.proyecto.panaderia.exceptions.SucursalNotFoundException;
import com.proyecto.panaderia.mapper.ProductoSucursalDTOMapper;
import com.proyecto.panaderia.repository.ProductoSucursalRepositorio;
import com.proyecto.panaderia.repository.ProductosRepositorio;
import com.proyecto.panaderia.repository.SucursalesRepositorio;
import com.proyecto.panaderia.request.ProductoSucursalRequest;
import com.proyecto.panaderia.services.ProductoSucursalServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductoSucursalServicioImpl implements ProductoSucursalServicio {
    private final ProductoSucursalDTOMapper productoSucursalDTOMapper;
    private final ProductoSucursalRepositorio productoSucursalRepositorio;
    private final SucursalesRepositorio sucursalesRepositorio;
    private final ProductosRepositorio productosRepositorio;

    @Override
    public List<ProductoSucursalDTO> getProductosSucursal() {
        return productoSucursalRepositorio.findAll()
                .stream()
                .map(productoSucursalDTOMapper)
                .toList();
    }

    @Override
    public Optional<ProductoSucursalDTO> getProductosSucursalById(Integer id) {
        return productoSucursalRepositorio.findById(id)
                .map(productoSucursalDTOMapper)
                .map(Optional::of)
                .orElseThrow(()-> new ProductoNotFoundException("No se encontro el producto con id: "+id));
    }

    @Override
    public List<ProductoSucursalDTO> getProductoSucursalById(Integer id) {
        return productoSucursalRepositorio.findBySucursalesId(id)
                .stream()
                .map(productoSucursalDTOMapper)
                .toList();
    }
    @Override
    public Optional<ProductoSucursalDTO> getProductolBySucursalId(Integer idproducto, Integer idsucursal) {
        return productoSucursalRepositorio.findBySucursalesIdAndProductosId(idsucursal,idproducto)
                .map(productoSucursalDTOMapper)
                .map(Optional::of)
                .orElseThrow(()-> new ProductoNotFoundException("No se encontro el producto con id: "+idproducto));
    }

    @Override
    public List<ProductoSucursalDTO> getProductosByCategoriaandSucursal(Integer idcategoria, Integer sucursalId) {
        return productoSucursalRepositorio.findBySucursalesIdAndCategoriasId(idcategoria,sucursalId)
                .stream()
                .map(productoSucursalDTOMapper)
                .toList();
    }

    @Override
    public void saveProductoSucursal(ProductoSucursalRequest productoSucursalRequest) {
        Optional<ProductoSucursal> existente = productoSucursalRepositorio.findByProductoIdAndSucursalId(productoSucursalRequest.getIdProducto(), productoSucursalRequest.getIdSucursal());
        if (existente.isPresent()) {
            throw new ProductoSucursalExistException("El producto ya existe en la sucursal");
        }
        ProductoSucursal productoSucursal = new ProductoSucursal();
        productoSucursal.setPrecioLocal(productoSucursalRequest.getPrecioLocal());
        productoSucursal.setStock(productoSucursalRequest.getStock());
        productoSucursal.setStatus(true);
        productoSucursal.setProductos(productosRepositorio.findById(productoSucursalRequest.getIdProducto()).orElseThrow(()-> new ProductoNotFoundException("No se encontro el producto con id: "+productoSucursalRequest.getIdProducto())));
        productoSucursal.setSucursales(sucursalesRepositorio.findById(productoSucursalRequest.getIdSucursal()).orElseThrow(()-> new SucursalNotFoundException("No se encontro la sucursal con id: "+productoSucursalRequest.getIdSucursal())));
        productoSucursalRepositorio.save(productoSucursal);

    }

    @Override
    public void updateProductoSucursal(ProductoSucursalRequest productoSucursalRequest,Integer id) {
        Optional<ProductoSucursal> existente = productoSucursalRepositorio.findByProductoIdAndSucursalIdExcludingId(
                productoSucursalRequest.getIdProducto(), productoSucursalRequest.getIdSucursal(), id);

        if (existente.isPresent()) {
            throw new ProductoSucursalExistException("El producto ya está asignado a esta sucursal.");
        }

        ProductoSucursal productoSucursal = productoSucursalRepositorio.findById(id).orElseThrow(()-> new ProductoNotFoundException("No se encontro el productoSucursal con id: "+id));
        productoSucursal.setPrecioLocal(productoSucursalRequest.getPrecioLocal());
        productoSucursal.setStock(productoSucursalRequest.getStock());
        productoSucursal.setStatus(true);
        productoSucursal.setProductos(productosRepositorio.findById(productoSucursalRequest.getIdProducto()).orElseThrow(()-> new ProductoNotFoundException("No se encontro el producto con id: "+productoSucursalRequest.getIdProducto())));
        productoSucursal.setSucursales(sucursalesRepositorio.findById(productoSucursalRequest.getIdSucursal()).orElseThrow(()-> new SucursalNotFoundException("No se encontro la sucursal con id: "+productoSucursalRequest.getIdSucursal())));
        productoSucursalRepositorio.save(productoSucursal);
    }

    @Override
    public void deleteProductoSucursalById(Integer id) {
        ProductoSucursal productoSucursal = productoSucursalRepositorio.findById(id).orElseThrow(()-> new ProductoNotFoundException("No se encontro el productoSucursal con id: "+id));
        productoSucursal.setStatus(false);
        productoSucursalRepositorio.save(productoSucursal);
    }
}
