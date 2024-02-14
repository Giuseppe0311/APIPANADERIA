package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductosRepositorio extends JpaRepository<Productos,Integer> {

    @Query(value = "SELECT * FROM productos" +
            " where empresa_id= ? and status=true " +
            "order by id desc",nativeQuery = true)
    List<Productos> getProductosByEmpresa(Integer idempresa);

    // Utilizando SQL nativo
    @Query(value = "SELECT * FROM productos WHERE LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) AND empresa_id = ?2 and status=true", nativeQuery = true)
    Optional<Productos> findByNombreIgnoreCaseAndTrimAndEmpresaId(String nombre, Integer empresaId);

    @Query(value = "SELECT * FROM productos WHERE LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) AND empresa_id = ?2 AND id <> ?3 AND status = true", nativeQuery = true)
    Optional<Productos> findByNombreIgnoreCaseAndTrimAndEmpresaIdExcludingId(String nombre, Integer empresaId, Integer id);

}
