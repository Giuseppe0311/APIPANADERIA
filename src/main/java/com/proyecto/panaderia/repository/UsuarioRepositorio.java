package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.dto.UsuarioAdminEmpresaDTO;
import com.proyecto.panaderia.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuarios,Integer> {
    @Query(value = "SELECT * FROM usuarios where usuario=?1 and estado=true",nativeQuery = true)
    Optional<Usuarios> findByUsuario(String usuario);
    @Query(value = "SELECT *\n" +
            "FROM usuarios\n" +
            "WHERE empresa_id IS NOT NULL AND empresa_id <> '' \n" +
            "AND (sucursal_id IS NULL OR sucursal_id = '') AND estado=true; ",nativeQuery = true)
    List<Usuarios> getAllUserOfEmpresas();
    @Query(value = "SELECT *\n" +
            "FROM usuarios\n" +
            "WHERE empresa_id IS NOT NULL AND empresa_id <> '' \n" +
            "AND sucursal_id IS NOT NULL AND sucursal_id <> '' AND estado=true;", nativeQuery = true)
    List<Usuarios> getAllUserOfEmpresasAndSucursales();
    @Query(value = "SELECT * FROM usuarios WHERE " +
            "(LOWER(REPLACE(usuario, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) OR " +
            "LOWER(REPLACE(dni, ' ', '')) = LOWER(REPLACE(?2, ' ', '')) OR " +
            "LOWER(REPLACE(correo, ' ', '')) = LOWER(REPLACE(?3, ' ', ''))) AND estado = true", nativeQuery = true)
    Optional<Usuarios> findByUsuarioOrDniOrCorreoIgnoreCaseAndTrim(String usuario, String dni, String correo);

    @Query(value = "SELECT * FROM usuarios WHERE " +
            "(LOWER(REPLACE(usuario, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) OR " +
            "LOWER(REPLACE(dni, ' ', '')) = LOWER(REPLACE(?2, ' ', '')) OR " +
            "LOWER(REPLACE(correo, ' ', '')) = LOWER(REPLACE(?3, ' ', ''))) AND " +
            "estado = true AND id <> ?4", nativeQuery = true)
    Optional<Usuarios> findByUsuarioOrDniOrCorreoIgnoreCaseAndTrimExcludingId(String usuario, String dni, String correo, Integer id);


}
