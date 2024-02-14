package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Sucursales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SucursalesRepositorio extends JpaRepository<Sucursales,Integer> {
    @Query(value = "SELECT * FROM sucursales WHERE empresa_id= ? and status=true order by id desc",nativeQuery = true)
    List<Sucursales> findByEmpresaId(Integer id);
    @Query(value = "SELECT * FROM sucursales WHERE empresa_id = ?1 AND status = true AND (LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?2, ' ', '')) OR REPLACE(telefono, ' ', '') = REPLACE(?3, ' ', '') OR LOWER(REPLACE(direccion, ' ', '')) = LOWER(REPLACE(?4, ' ', '')))", nativeQuery = true)
    Optional<Sucursales> findByNombreOrTelefonoOrDireccionIgnoreCaseAndTrimAndEmpresaId(Integer empresaId,String nombre, String telefono, String direccion);
    @Query(value = "SELECT * FROM sucursales WHERE empresa_id = ?2 AND id <> ?3 AND status = true AND (LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) OR REPLACE(telefono, ' ', '') = REPLACE(?4, ' ', '') OR LOWER(REPLACE(direccion, ' ', '')) = LOWER(REPLACE(?5, ' ', '')))", nativeQuery = true)
    Optional<Sucursales> findByNombreOrTelefonoOrDireccionIgnoreCaseAndTrimAndEmpresaIdExcludingId(String nombre, Integer empresaId, Integer id, String telefono, String direccion);
}
