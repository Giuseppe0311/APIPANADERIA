package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.UnidadesMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnidadMedidaRepositorio extends JpaRepository<UnidadesMedida,Integer> {
    @Query(value = "SELECT * FROM unidades_medida where empresa_id=? and status=true order by id desc",nativeQuery = true)
    List<UnidadesMedida> getUnidadMedidaByEmpresa(Integer id);

    @Query(value = "SELECT * FROM unidades_medida where status=true order by id desc",nativeQuery = true)
    List<UnidadesMedida> getUnidadMedida();
    @Query(value = "SELECT * FROM unidades_medida WHERE LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) AND empresa_id = ?2 AND id <> ?3 AND status=true", nativeQuery = true)
    Optional<UnidadesMedida> findByNombreAndEmpresaIdExcludingId(String nombre, Integer empresaId, Integer id);
    @Query(value = "SELECT * FROM unidades_medida WHERE LOWER(REPLACE(nombre, ' ', '')) = LOWER(REPLACE(?1, ' ', '')) AND empresa_id = ?2 AND status = true", nativeQuery = true)
    Optional<UnidadesMedida> findByNombreAndEmpresaId(String nombre, Integer empresaId);


}
