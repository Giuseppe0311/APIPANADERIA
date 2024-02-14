package com.proyecto.panaderia.mapper;

import com.proyecto.panaderia.dto.PagosDTO;
import com.proyecto.panaderia.entity.Pagos;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PagosDTOMapper implements Function<Pagos, PagosDTO> {

    @Override
    public PagosDTO apply(Pagos pagos) {
        return new PagosDTO(
                pagos.getId(),
                pagos.getFechadepago(),
                pagos.getTipo_pago(),
                pagos.getMonto(),
                pagos.getCompra().getId()
        );
    }
}
