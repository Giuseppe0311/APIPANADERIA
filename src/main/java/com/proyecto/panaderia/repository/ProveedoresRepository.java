package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedores,Integer> {
    @Query(value = "SELECT * FROM proveedores where sucursal_id=?1 and estado=true",nativeQuery = true)
    List<Proveedores> getProveedoresBySucursal(Integer id);

    @Query(value = "SELECT * FROM proveedores WHERE estado = true AND sucursal_id = ?4 AND (LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) OR REPLACE(telefono, ' ', '') = REPLACE(?2, ' ', '') OR LOWER(email) = LOWER(?3))", nativeQuery = true)
    Optional<Proveedores> findByNombreOrTelefonoOrEmailIgnoreCaseAndTrimAndSucursalId(String nombre, String telefono, String email, Integer sucursalId);

    @Query(value = "SELECT * FROM proveedores WHERE estado = true AND sucursal_id = ?4 AND id <> ?5 AND (LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) OR REPLACE(telefono, ' ', '') = REPLACE(?2, ' ', '') OR LOWER(email) = LOWER(?3))", nativeQuery = true)
    Optional<Proveedores> findByNombreOrTelefonoOrEmailIgnoreCaseAndTrimAndSucursalIdExcludingId(String nombre, String telefono, String email, Integer sucursalId, Integer id);
}
