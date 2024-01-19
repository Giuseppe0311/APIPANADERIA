package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepositorio extends JpaRepository<Empresa,Integer> {
}
