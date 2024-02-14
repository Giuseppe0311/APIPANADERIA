package com.proyecto.panaderia.mapper;

import com.proyecto.panaderia.dto.ProductosDTO;
import com.proyecto.panaderia.entity.Productos;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductosDTOMapper implements Function<Productos, ProductosDTO> {
    @Override
    public ProductosDTO apply(Productos productos) {
        return new ProductosDTO(
                productos.getId(),
                productos.getNombre(),
                productos.getDescripcion(),
                productos.getImagen(),
                productos.getPrecioBase(),
                productos.getCategoria().getNombre(),
                productos.getCategoria().getId(),
                productos.getUnidadesMedida().getNombre(),
                productos.getUnidadesMedida().getId(),
                productos.getStock()
        );
    }
}
