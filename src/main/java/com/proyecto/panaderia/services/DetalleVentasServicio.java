package com.proyecto.panaderia.services;

import com.proyecto.panaderia.dto.DetalleVentasDTO;

import java.util.List;

public interface DetalleVentasServicio {
    List<DetalleVentasDTO> getDetalleVentas(Integer idVenta);
}
