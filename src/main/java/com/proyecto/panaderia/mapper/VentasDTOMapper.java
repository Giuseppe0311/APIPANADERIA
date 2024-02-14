package com.proyecto.panaderia.mapper;


import com.proyecto.panaderia.dto.VentasDTO;
import com.proyecto.panaderia.entity.Ventas;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class VentasDTOMapper implements Function<Ventas, VentasDTO> {
    @Override
    public VentasDTO apply(Ventas ventas) {
        return new VentasDTO(
                ventas.getId(),
                ventas.getUsuario().getId(),
                ventas.getTipo_pago(),
                ventas.getTipo_comprobante(),
                ventas.getNum_comprobante(),
                ventas.getTotal(),
                ventas.getFecha(),
                ventas.getUsuario().getNombre(),
                ventas.getUsuario().getCorreo(),
                ventas.getUsuario().getTelefono(),
                ventas.getSucursal().getNombre(),
                ventas.getStatus()
                );
    }
}
