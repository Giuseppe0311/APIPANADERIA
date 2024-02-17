package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.dto.VentasDTO;
import com.proyecto.panaderia.dto.reports.ProductosMasVendidosDTO;
import com.proyecto.panaderia.dto.reports.VentasPorTipoPagoDTO;
import com.proyecto.panaderia.entity.DetalleVentas;
import com.proyecto.panaderia.entity.Ventas;
import com.proyecto.panaderia.exceptions.ProductoNotFoundException;
import com.proyecto.panaderia.exceptions.ProductoSucursalExistException;
import com.proyecto.panaderia.exceptions.SucursalNotFoundException;
import com.proyecto.panaderia.exceptions.UsuarioNotFoundException;
import com.proyecto.panaderia.mapper.VentasDTOMapper;
import com.proyecto.panaderia.repository.*;
import com.proyecto.panaderia.request.VentasRequest;
import com.proyecto.panaderia.services.VentasServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VentasServiceImpl implements VentasServicio {
    private final UsuarioRepositorio usuarioRepositorio;
    private final SucursalesRepositorio sucursalesRepositorio;
    private final VentasRepositorio ventasRepositorio;
    private final DetalleVentasRepositorio detalleVentasRepositorio;
    private final ProductosRepositorio productosRepositorio;
    private final VentasDTOMapper ventasDTOMapper;
    private final ProductoSucursalRepositorio productoSucursalRepositorio;


    @Override
    public List<VentasDTO> listarVentas(Integer idSucursal,String estado){
        if (estado.equals("RESERVADO")){
            return ventasRepositorio.findAllReservados(idSucursal)
                    .stream()
                    .map(ventasDTOMapper)
                    .toList();
        } else if (estado.equals("ANULADO")){
            return ventasRepositorio.findAllInactive(idSucursal)
                    .stream()
                    .map(ventasDTOMapper)
                    .toList();
        } else if (estado.equals("PAGADO")){
            return ventasRepositorio.findAllIpagado(idSucursal)
                    .stream()
                    .map(ventasDTOMapper)
                    .toList();
        } else {
            return ventasRepositorio.findAll()
                    .stream()
                    .map(ventasDTOMapper)
                    .toList();
        }
    }

    @Override
    public VentasDTO listarVentaPorId(Integer idVenta) {
        return ventasRepositorio.findById(idVenta)
                .map(ventasDTOMapper)
                .orElseThrow(() -> new RuntimeException("No se encontro la venta con el id asignado"));
    }

    @Override
    public List<ProductosMasVendidosDTO> productosMasVendidos(Integer idSucursal) {
        return ventasRepositorio.findTop5ProductosPorSucursal(idSucursal);
    }

    @Override
    public List<VentasPorTipoPagoDTO> ventasPorTipoPago(Integer idSucursal) {
        return ventasRepositorio.findVentasPorTipoPago(idSucursal);
    }

    @Override
    public void registrarVenta(VentasRequest ventasRequest) {
        Ventas ventas = new Ventas();
        ventas.setSucursal(sucursalesRepositorio.findById(ventasRequest.getIdsucursal()).orElseThrow(
                () -> new SucursalNotFoundException("No se encontro la sucursal con el id asignado")));
        ventas.setUsuario(usuarioRepositorio.findById(ventasRequest.getIdusuario()).orElseThrow(
                () -> new UsuarioNotFoundException("No se encontro el usuario con el id asignado")));
        ventas.setTipo_pago(ventasRequest.getTipo_pago());
        ventas.setTipo_comprobante(ventasRequest.getTipo_comprobante());
        ventas.setNum_comprobante(ventasRequest.getNum_comprobante());
        ventas.setTotal(ventasRequest.getTotal());
        ventas.setStatus("RESERVADO");
        ventasRepositorio.save(ventas);

        ventasRequest.getProductos().forEach(detalleVentasRequest -> {
            DetalleVentas detalleVentas = new DetalleVentas();
            detalleVentas.setVentas(ventas);
            detalleVentas.setProductos(productosRepositorio.findById(detalleVentasRequest.getIdproducto()).orElseThrow(
                    () -> new ProductoNotFoundException("No se encontro el producto con el id asignado")));
            detalleVentas.setCantidad(detalleVentasRequest.getCantidad());
            detalleVentas.setPrecio_venta(detalleVentasRequest.getPrecio_venta());
            detalleVentas.setSubtotal(detalleVentasRequest.getSubtotal());
            detalleVentasRepositorio.save(detalleVentas);
            //Actualizar stock
            productoSucursalRepositorio.findByProductoIdAndSucursalId(detalleVentasRequest.getIdproducto(),ventasRequest.getIdsucursal())
                    .ifPresent(productoSucursal -> {
                        if (productoSucursal.getStock()<detalleVentasRequest.getCantidad()){
                            throw new ProductoSucursalExistException("No hay stock suficiente para el producto: "+productoSucursal.getProductos().getNombre());
                        }
                        productoSucursal.setStock(productoSucursal.getStock()-detalleVentasRequest.getCantidad());
                        productoSucursalRepositorio.save(productoSucursal);
                    });
        }
        );
    }

        @Override
        public void editarVenta (Integer idVenta){
        Ventas venta =ventasRepositorio.findById(idVenta).orElseThrow(()->
                    new RuntimeException("No se encontro la venta con el id asignado"));
            venta.setStatus("PAGADO");
            ventasRepositorio.save(venta);
        }

        @Override
        public void eliminarVenta (Integer idVenta){
            Ventas venta =ventasRepositorio.findById(idVenta).orElseThrow(()->
                    new RuntimeException("No se encontro la venta con el id asignado"));
            venta.setStatus("ANULADO");
            ventasRepositorio.save(venta);
        }
    }
