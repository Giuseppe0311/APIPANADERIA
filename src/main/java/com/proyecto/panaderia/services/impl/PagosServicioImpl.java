package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.entity.Pagos;
import com.proyecto.panaderia.repository.ComprasRepositorio;
import com.proyecto.panaderia.repository.PagosRepository;
import com.proyecto.panaderia.request.PagosRequest;
import com.proyecto.panaderia.services.PagosServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagosServicioImpl  implements PagosServicio {
    private final PagosRepository pagosRepository;
    private final ComprasRepositorio comprasRepositorio;

    @Override
    public List<Pagos> listarPagos() {
        return null;
    }

    @Override
    public List<Pagos> listarPagosPorSucursal(Integer idsucursal) {
        return pagosRepository.getPagosBySucursal(idsucursal);
    }

    @Override
    public void guardarPago(PagosRequest pagosRequest) {
        Pagos pagos = new Pagos();
        pagos.setTipo_pago(pagosRequest.getTipo_pago());
        pagos.setMonto(Double.parseDouble(pagosRequest.getMonto_pagado()));
        pagos.setFechadepago(pagosRequest.getFechadepago());
        pagos.setCompra(comprasRepositorio.findById(pagosRequest.getIdcompra()).orElseThrow(()->
                new RuntimeException("No se encontro la compra")));
        pagosRepository.save(pagos);
    }
}
