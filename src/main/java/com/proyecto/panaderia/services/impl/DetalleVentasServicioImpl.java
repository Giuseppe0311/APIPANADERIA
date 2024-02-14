package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.dto.DetalleVentasDTO;
import com.proyecto.panaderia.mapper.DetalleVentasDTOMapper;
import com.proyecto.panaderia.repository.DetalleVentasRepositorio;
import com.proyecto.panaderia.services.DetalleVentasServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleVentasServicioImpl implements DetalleVentasServicio {
    private  final DetalleVentasRepositorio detalleVentasRepositorio;
    private  final DetalleVentasDTOMapper detalleVentasDTOMapper;
    @Override
    public List<DetalleVentasDTO> getDetalleVentas(Integer idVenta) {
        return detalleVentasRepositorio.findByIdVenta(idVenta)
                .stream()
                .map(detalleVentasDTOMapper)
                .toList();
    }
}
