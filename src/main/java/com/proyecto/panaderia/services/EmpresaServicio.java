package com.proyecto.panaderia.services;

import com.proyecto.panaderia.entity.Empresas;

import java.util.List;
import java.util.Optional;

public interface EmpresaServicio {
    List<Empresas> getEmpresas();
    Optional<Empresas> getEmpresaById(Integer id);
}
