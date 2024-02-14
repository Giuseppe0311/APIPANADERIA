package com.proyecto.panaderia.mapper;

import com.proyecto.panaderia.dto.DetalleVentasDTO;
import com.proyecto.panaderia.entity.DetalleVentas;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DetalleVentasDTOMapper implements Function<DetalleVentas, DetalleVentasDTO> {
    @Override
    public DetalleVentasDTO apply(DetalleVentas detalleVentas) {
        return new DetalleVentasDTO(
                detalleVentas.getId(),
                detalleVentas.getCantidad(),
                detalleVentas.getPrecio_venta(),
                detalleVentas.getSubtotal(),
                detalleVentas.getProductos().getNombre(),
                detalleVentas.getProductos().getId()
        );
    }
}
