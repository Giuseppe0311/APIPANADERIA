package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.dto.SucursalDTO;
import com.proyecto.panaderia.entity.Sucursales;
import com.proyecto.panaderia.exceptions.EmpresaNotFoundException;
import com.proyecto.panaderia.exceptions.SucursalExistException;
import com.proyecto.panaderia.exceptions.SucursalNotFoundException;
import com.proyecto.panaderia.mapper.SucursalDTOMapper;
import com.proyecto.panaderia.repository.EmpresaRepositorio;
import com.proyecto.panaderia.repository.SucursalesRepositorio;
import com.proyecto.panaderia.request.SucursalRequest;
import com.proyecto.panaderia.services.SucursalServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SucursalServicioImpl implements SucursalServicio {
    private final SucursalDTOMapper sucursalDTOMapper;
    private final SucursalesRepositorio sucursalesRepositorio;
    private final EmpresaRepositorio empresaRepositorio;

    @Override
    public List<SucursalDTO> getSucursales() {
        return sucursalesRepositorio.findAll().stream()
                .map(sucursalDTOMapper)
                .toList();
    }

    @Override
    public Optional<SucursalDTO> getSucursal(Integer id) {
        return sucursalesRepositorio
                .findById(id)
                .map(sucursalDTOMapper)
                .map(Optional::of)
                .orElseThrow(() ->
                        new SucursalNotFoundException("No se encontro la sucursal con el id"));
    }

    @Override
    public List<SucursalDTO> getSucursalesByEmpresa(Integer id) {
        return sucursalesRepositorio.findByEmpresaId(id).stream()
                .map(sucursalDTOMapper)
                .toList();
    }

    @Override
    public void saveSucursal(SucursalRequest sucursalRequest) {
        // Normalizar los datos de entrada
        String nombreNormalizado = sucursalRequest.getNombre().trim().toLowerCase();
        // Suponiendo que el teléfono no necesita eliminación de espacios pero lo incluimos por consistencia
        String telefonoNormalizado = sucursalRequest.getTelefono().replace(" ", "");
        String direccionNormalizada = sucursalRequest.getDireccion().trim().toLowerCase();
        // Verificar si ya existe una sucursal con los mismos datos en la empresa
        Optional<Sucursales> sucursalExistente = sucursalesRepositorio.findByNombreOrTelefonoOrDireccionIgnoreCaseAndTrimAndEmpresaId(sucursalRequest.getIdempresa(),nombreNormalizado, telefonoNormalizado, direccionNormalizada);

        if (sucursalExistente.isPresent()) {
            throw new SucursalExistException("Ya existe una sucursal con el mismo nombre, teléfono o dirección en la empresa.");
        }
        Sucursales sucursal = new Sucursales();
        sucursal.setNombre(sucursalRequest.getNombre());
        sucursal.setDireccion(sucursalRequest.getDireccion());
        sucursal.setTelefono(sucursalRequest.getTelefono());
        sucursal.setInformacion(sucursalRequest.getInformacion());
        sucursal.setStatus(true);
        sucursal.setEmpresa(empresaRepositorio.findById(sucursalRequest.getIdempresa())
                .orElseThrow(()->
                        new EmpresaNotFoundException("No se encontro la empresa con el id asignado")));
        sucursalesRepositorio.save(sucursal);
    }

    @Override
    public void updateSucursal(SucursalRequest sucursalRequest, Integer id) {
        Sucursales sucursales = sucursalesRepositorio.findById(id)
                .orElseThrow(()->
                        new SucursalNotFoundException("No se encontro la sucursal con el id asignado"));
        String nombreNormalizado = sucursalRequest.getNombre().trim().toLowerCase();
        String telefonoNormalizado = sucursalRequest.getTelefono().replace(" ", "");
        String direccionNormalizada = sucursalRequest.getDireccion().trim().toLowerCase();
        Optional<Sucursales> sucursalExistente = sucursalesRepositorio.findByNombreOrTelefonoOrDireccionIgnoreCaseAndTrimAndEmpresaIdExcludingId(nombreNormalizado, sucursalRequest.getIdempresa(), id, telefonoNormalizado, direccionNormalizada);
        if (sucursalExistente.isPresent() && sucursalExistente.get().getId() != id) {
            throw new SucursalExistException("Ya existe una sucursal con el mismo nombre, teléfono o dirección en la empresa.");
        }
        sucursales.setNombre(sucursalRequest.getNombre());
        sucursales.setDireccion(sucursalRequest.getDireccion());
        sucursales.setTelefono(sucursalRequest.getTelefono());
        sucursales.setInformacion(sucursalRequest.getInformacion());
        sucursales.setEmpresa(empresaRepositorio.findById(sucursalRequest.getIdempresa())
                .orElseThrow(()->
                        new EmpresaNotFoundException("No se encontro la empresa con el id asignado")));
        sucursalesRepositorio.save(sucursales);
    }

    @Override
    public void deleteSucursal(Integer id) {
        Sucursales sucursales = sucursalesRepositorio.findById(id)
                .orElseThrow(()->
                        new SucursalNotFoundException("No se encontro la sucursal con el id asignado"));
        sucursales.setStatus(false);
        sucursalesRepositorio.save(sucursales);
    }
}
