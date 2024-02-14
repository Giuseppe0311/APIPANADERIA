package com.proyecto.panaderia.services;

import com.proyecto.panaderia.entity.Categorias;
import com.proyecto.panaderia.request.CategoriaRequest;

import java.util.List;
import java.util.Optional;

public interface CategoriaServicio {
    List<Categorias> getCategoriasbyIdEmpresa(Integer idempresa);

    Optional<Categorias> getCategoriaById(Integer id);

    void updateCategoria(CategoriaRequest categoriaRequest, Integer id);

    void saveCategoria(CategoriaRequest categoriaRequest);

    void deleteCategoria(Integer id);


}
