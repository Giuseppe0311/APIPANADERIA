package com.proyecto.panaderia.mapper;

import com.proyecto.panaderia.dto.ProductoSucursalDTO;
import com.proyecto.panaderia.entity.ProductoSucursal;
import com.proyecto.panaderia.entity.Productos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductoSucursalDTOMapper implements Function<ProductoSucursal, ProductoSucursalDTO> {
    private final ProductosDTOMapper productosDTOMapper;
    @Override
    public ProductoSucursalDTO apply(ProductoSucursal productoSucursal) {
        return new ProductoSucursalDTO(
                productoSucursal.getSucursales().getId(),
                productoSucursal.getSucursales().getNombre(),
                productosDTOMapper.apply(productoSucursal.getProductos()),
                productoSucursal.getPrecioLocal(),
                productoSucursal.getStock()
        );
    }
}
