package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Empresas;
import com.proyecto.panaderia.entity.Empresas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresas,Integer> {
}
