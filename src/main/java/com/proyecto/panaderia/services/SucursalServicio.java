package com.proyecto.panaderia.services;

import com.proyecto.panaderia.dto.SucursalDTO;
import com.proyecto.panaderia.request.SucursalRequest;

import java.util.List;
import java.util.Optional;

public interface SucursalServicio {
    List<SucursalDTO> getSucursales();
    Optional<SucursalDTO> getSucursal(Integer id);
    List<SucursalDTO> getSucursalesByEmpresa(Integer id);
    void saveSucursal(SucursalRequest sucursalRequest);
    void updateSucursal(SucursalRequest sucursalRequest, Integer id);
    void deleteSucursal(Integer id);
}
