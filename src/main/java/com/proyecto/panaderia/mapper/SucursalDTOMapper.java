package com.proyecto.panaderia.mapper;

import com.proyecto.panaderia.dto.SucursalDTO;
import com.proyecto.panaderia.entity.Sucursales;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SucursalDTOMapper implements Function<Sucursales, SucursalDTO> {
    @Override
    public SucursalDTO apply(Sucursales sucursales) {
        return new SucursalDTO(
                sucursales.getId(),
                sucursales.getNombre(),
                sucursales.getDireccion(),
                sucursales.getTelefono(),
                sucursales.getInformacion());
    }
}
