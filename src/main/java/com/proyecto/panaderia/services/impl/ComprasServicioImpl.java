package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.entity.Compras;
import com.proyecto.panaderia.entity.DetalleCompras;
import com.proyecto.panaderia.repository.ComprasRepositorio;
import com.proyecto.panaderia.repository.DetalleComprasRepositorio;
import com.proyecto.panaderia.repository.ProveedoresRepository;
import com.proyecto.panaderia.repository.SucursalesRepositorio;
import com.proyecto.panaderia.request.ComprasRequest;
import com.proyecto.panaderia.services.ComprasServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComprasServicioImpl implements ComprasServicio {
    private final DetalleComprasRepositorio detalleComprasRepositorio;
    private final ComprasRepositorio comprasRepositorio;
    private final SucursalesRepositorio sucursalesRepositorio;
    private final ProveedoresRepository proveedoresRepository;
    @Override
    public List<Compras> listarCompras() {
        return comprasRepositorio.findAll();
    }

    @Override
    public Compras getCompraPorId(Integer id) {
        return comprasRepositorio.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontro la compra")
        );
    }

    @Override
    public List<Compras> listarComprasPorSucursal(Integer id) {
        return comprasRepositorio.getComprasBySucursal(id);
    }

    @Override
    public void guardarCompra(ComprasRequest comprasRequest) {
        Compras compras = new Compras();
        compras.setProveedor(proveedoresRepository.findById(comprasRequest.getIdproveedor()).orElseThrow(
                () -> new RuntimeException("No se encontro el proveedor")
        ));
        compras.setTotal(comprasRequest.getTotalCompra());
        compras.setSucursal(sucursalesRepositorio.findById(comprasRequest.getIdsucursal()).orElseThrow(
                () -> new RuntimeException("No se encontro la sucursal")
        ));
        compras.setFecha(comprasRequest.getFecha());
        compras.setTipo_pago(comprasRequest.getTipo_pago());
        compras.setEstado_pago(comprasRequest.getEstado_pago());
        compras.setTipo_comprobante(comprasRequest.getTipo_comprobante());
        compras.setNum_comprobante(comprasRequest.getNum_comprobante());
       comprasRepositorio.save(compras);
//       DETALLES
        comprasRequest.getDetalleCompras()
                .forEach(detalleComprasRequest -> {
                    DetalleCompras detalleCompras = new DetalleCompras();
                    detalleCompras.setCantidad(detalleComprasRequest.getCantidad());
                    detalleCompras.setPrecio(detalleComprasRequest.getPrecio());
                    detalleCompras.setProducto(detalleComprasRequest.getProducto());
                    detalleCompras.setCompra(compras);
                    detalleCompras.setSubtotal(detalleComprasRequest.getSubtotal());
                    detalleComprasRepositorio.save(detalleCompras);
                });
    }

    @Override
    public void actualizaEstadoDeCompra(String estado, Integer id) {
        Compras compras = comprasRepositorio.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontro la compra")
        );
        compras.setEstado_pago(estado);
        comprasRepositorio.save(compras);
    }

    @Override
    public void eliminarCompra(Integer id) {
        Compras compras = comprasRepositorio.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontro la compra")
        );
        compras.setEstado_pago("ANULADO");
        comprasRepositorio.save(compras);
    }
}
