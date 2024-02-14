package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categorias,Integer> {
    @Query(value = "SELECT * FROM categorias where empresa_id=? and status=true order by id desc",nativeQuery = true)
    List<Categorias> getCategoriasByEmpresa (Integer idempresa);
    @Query(value = "SELECT * FROM categorias WHERE LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) AND empresa_id = ?2 AND status = true",nativeQuery = true)
    Optional<Categorias> findByNombreIgnoreCaseAndTrimAndEmpresaId(String nombre, Integer empresaId);

    @Query(value = "SELECT * FROM categorias WHERE LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) AND empresa_id = ?2 AND id <> ?3 AND status = true", nativeQuery = true)
    Optional<Categorias> findByNombreIgnoreCaseAndTrimAndEmpresaIdExcludingIdAndStatusTrue(String nombre, Integer empresaId, Integer categoriaId);

}
