package com.proyecto.panaderia.services;

import com.proyecto.panaderia.entity.Empresas;
import com.proyecto.panaderia.request.EmpresaRequest;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface EmpresaServicio {
    List<Empresas> getEmpresas();
    Optional<Empresas> getEmpresaById(Integer id);
    @Transactional
    void updateEmpresa(EmpresaRequest empresaRequest,Integer id);
    @Transactional
    void saveEmpresa(EmpresaRequest empresaRequest);
    void deleteEmpresaById(Integer id);
}
