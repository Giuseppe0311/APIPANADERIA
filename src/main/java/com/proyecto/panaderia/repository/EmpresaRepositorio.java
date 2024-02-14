package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Empresas;
import com.proyecto.panaderia.entity.Empresas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresas,Integer> {

    @Query(value = "SELECT * FROM empresas WHERE status = true order by id desc",nativeQuery = true)
    List<Empresas> findAll();
    @Query(value = "SELECT * FROM empresas WHERE status = true AND (LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) OR LOWER(REPLACE(direccion, ' ', '')) = LOWER(REPLACE(?2, ' ', '')) OR REPLACE(telefono, ' ', '') = REPLACE(?3, ' ', ''))", nativeQuery = true)
    Optional<Empresas> findByNombreOrDireccionOrTelefonoIgnoreCaseAndTrim(String nombre, String direccion, String telefono);

    @Query(value = "SELECT * FROM empresas WHERE status = true AND id <> ?4 AND (LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) OR LOWER(REPLACE(direccion, ' ', '')) = LOWER(REPLACE(?2, ' ', '')) OR REPLACE(telefono, ' ', '') = REPLACE(?3, ' ', ''))", nativeQuery = true)
    Optional<Empresas> findByNombreOrDireccionOrTelefonoIgnoreCaseAndTrimExcludingId(String nombre, String direccion, String telefono, Integer id);

}
