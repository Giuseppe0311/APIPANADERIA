package com.proyecto.panaderia.services;

import com.proyecto.panaderia.dto.VentasDTO;
import com.proyecto.panaderia.dto.reports.ProductosMasVendidosDTO;
import com.proyecto.panaderia.dto.reports.VentasPorTipoPagoDTO;
import com.proyecto.panaderia.entity.Ventas;
import com.proyecto.panaderia.request.VentasRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface VentasServicio {
    //TODO : PRIMERO EMPEZAR CON LO QUE TIENE QUE VER CON REGISTRO
    List<VentasDTO> listarVentas(Integer idSucursal,String estado);
    VentasDTO listarVentaPorId(Integer idVenta);
    List<ProductosMasVendidosDTO> productosMasVendidos(Integer idSucursal);
    List<VentasPorTipoPagoDTO> ventasPorTipoPago(Integer idSucursal);
    @Transactional
    void registrarVenta(VentasRequest ventasRequest);
    @Transactional
    void editarVenta(Integer idVenta);
    @Transactional
    void eliminarVenta(Integer idVenta);
}
