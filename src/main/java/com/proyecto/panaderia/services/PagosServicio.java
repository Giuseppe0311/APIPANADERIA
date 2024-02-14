package com.proyecto.panaderia.services;

import com.proyecto.panaderia.entity.Pagos;
import com.proyecto.panaderia.request.PagosRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PagosServicio {
    List<Pagos> listarPagos();

    List<Pagos> listarPagosPorSucursal(Integer idsucursal);
    @Transactional
    void guardarPago(PagosRequest pagosRequest);
}
