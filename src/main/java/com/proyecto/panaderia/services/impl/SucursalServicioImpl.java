package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.dto.SucursalDTO;
import com.proyecto.panaderia.exceptions.SucursalNotFoundException;
import com.proyecto.panaderia.mapper.SucursalDTOMapper;
import com.proyecto.panaderia.repository.SucursalesRepositorio;
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
                .orElseThrow(()->
                        new SucursalNotFoundException("No se encontro la sucursal con el id"));
    }

    @Override
    public List<SucursalDTO> getSucursalesByEmpresa(Integer id) {
        return sucursalesRepositorio.findByEmpresaId(id).stream()
                .map(sucursalDTOMapper)
                .toList();
    }
}
