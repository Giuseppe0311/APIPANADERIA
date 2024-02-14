package com.proyecto.panaderia.services;

import com.proyecto.panaderia.entity.Compras;
import com.proyecto.panaderia.request.ComprasRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ComprasServicio {
    List<Compras> listarCompras();
    Compras getCompraPorId(Integer id);
    List<Compras> listarComprasPorSucursal(Integer id);
    @Transactional
    void guardarCompra(ComprasRequest comprasRequest);
    @Transactional
    void actualizaEstadoDeCompra(String estado, Integer id);
    void eliminarCompra(Integer id);
}
