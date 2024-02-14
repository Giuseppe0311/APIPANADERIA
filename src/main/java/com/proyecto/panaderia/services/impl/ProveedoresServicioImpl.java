package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.entity.Proveedores;
import com.proyecto.panaderia.exceptions.ProveedorExistException;
import com.proyecto.panaderia.repository.ProveedoresRepository;
import com.proyecto.panaderia.repository.SucursalesRepositorio;
import com.proyecto.panaderia.request.ProveedoresRequest;
import com.proyecto.panaderia.services.ProveedoresServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProveedoresServicioImpl implements ProveedoresServicio {
    private final SucursalesRepositorio sucursalesRepositorio;
    private  final ProveedoresRepository proveedoresRepository;
    @Override
    public List<Proveedores> getProveedoresBySucursal(Integer id) {
        return proveedoresRepository.getProveedoresBySucursal(id);
    }

    @Override
    public Proveedores getProveedorById(Integer id) {
        return proveedoresRepository.findById(id).orElseThrow(()->
                new RuntimeException("Proveedor no encontrado"));
    }

    @Override
    public void saveProveedor(ProveedoresRequest proveedoresRequest) {
        String nombreNormalizado = proveedoresRequest.getNombre().trim().toLowerCase();
        String telefonoNormalizado = proveedoresRequest.getTelefono().replace(" ", "");
        String emailNormalizado = proveedoresRequest.getEmail().trim().toLowerCase();
        Integer sucursalId = proveedoresRequest.getIdSucursal();
        proveedoresRepository.findByNombreOrTelefonoOrEmailIgnoreCaseAndTrimAndSucursalId(nombreNormalizado, telefonoNormalizado, emailNormalizado, sucursalId).ifPresent(proveedor -> {
            throw new ProveedorExistException("Ya existe un proveedor con el mismo nombre, telefono o email");
        });
        Proveedores proveedor = new Proveedores();
        proveedor.setNombre(proveedoresRequest.getNombre());
        proveedor.setDireccion(proveedoresRequest.getDireccion());
        proveedor.setTelefono(proveedoresRequest.getTelefono());
        proveedor.setEmail(proveedoresRequest.getEmail());
        proveedor.setRUC(proveedoresRequest.getRuc());
        proveedor.setSucursal(sucursalesRepositorio.findById(proveedoresRequest.getIdSucursal()).orElseThrow(()->
                new RuntimeException("Sucursal no encontrada")));
        proveedor.setEstado(true);
        proveedoresRepository.save(proveedor);
    }

    @Override
    public void updateProveedor(Integer id,ProveedoresRequest proveedoresRequest) {
        Proveedores proveedor = proveedoresRepository.findById(id).orElseThrow(()->
                new RuntimeException("Proveedor no encontrado"));
        String nombreNormalizado = proveedoresRequest.getNombre().trim().toLowerCase();
        String telefonoNormalizado = proveedoresRequest.getTelefono().replace(" ", "");
        String emailNormalizado = proveedoresRequest.getEmail().trim().toLowerCase();
        Integer sucursalId = proveedoresRequest.getIdSucursal();
        proveedoresRepository.findByNombreOrTelefonoOrEmailIgnoreCaseAndTrimAndSucursalIdExcludingId(nombreNormalizado, telefonoNormalizado, emailNormalizado, sucursalId, id).ifPresent(p -> {
            throw new ProveedorExistException("Ya existe otro proveedor con el mismo nombre, teléfono, o email en la sucursal especificada.");
        });
        proveedor.setNombre(proveedoresRequest.getNombre());
        proveedor.setDireccion(proveedoresRequest.getDireccion());
        proveedor.setTelefono(proveedoresRequest.getTelefono());
        proveedor.setEmail(proveedoresRequest.getEmail());
        proveedor.setRUC(proveedoresRequest.getRuc());
        proveedor.setSucursal(sucursalesRepositorio.findById(proveedoresRequest.getIdSucursal()).orElseThrow(()->
                new RuntimeException("Sucursal no encontrada")));
        proveedoresRepository.save(proveedor);
    }

    @Override
    public void deleteProveedor(Integer id) {
        Proveedores proveedor = proveedoresRepository.findById(id).orElseThrow(()->
                new RuntimeException("Proveedor no encontrado"));
        proveedor.setEstado(false);
        proveedoresRepository.save(proveedor);
    }
}
