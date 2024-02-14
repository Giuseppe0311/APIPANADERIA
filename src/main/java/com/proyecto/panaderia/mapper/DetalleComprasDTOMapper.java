package com.proyecto.panaderia.mapper;

import com.proyecto.panaderia.dto.DetalleComprasDTO;
import com.proyecto.panaderia.entity.DetalleCompras;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DetalleComprasDTOMapper implements Function<DetalleCompras, DetalleComprasDTO> {
    @Override
    public DetalleComprasDTO apply(DetalleCompras detalleCompras) {
        return new DetalleComprasDTO(
                detalleCompras.getId(),
                detalleCompras.getProducto(),
                detalleCompras.getCantidad(),
                detalleCompras.getPrecio(),
                detalleCompras.getSubtotal()
        );
    }
}
