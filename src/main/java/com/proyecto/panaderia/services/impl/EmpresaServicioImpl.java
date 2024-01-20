package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.entity.Empresas;
import com.proyecto.panaderia.exceptions.EmpresaNotFoundException;
import com.proyecto.panaderia.repository.EmpresaRepositorio;
import com.proyecto.panaderia.services.EmpresaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaServicioImpl implements EmpresaServicio {
    private final EmpresaRepositorio empresaRepositorio;
    @Override
    public List<Empresas> getEmpresas() {
        return empresaRepositorio.findAll();
    }

    @Override
    public Optional<Empresas> getEmpresaById(Integer id) {
        return empresaRepositorio.findById(id)
                .map(Optional::of)
                .orElseThrow(()->
                        new EmpresaNotFoundException("No se encontro la empresa")
                );
    }
}
