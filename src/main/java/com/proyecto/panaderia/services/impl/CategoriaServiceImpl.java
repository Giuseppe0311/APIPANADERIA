package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.entity.Categorias;
import com.proyecto.panaderia.exceptions.CategoriaExistException;
import com.proyecto.panaderia.exceptions.CategoriaNotFoundException;
import com.proyecto.panaderia.exceptions.EmpresaNotFoundException;
import com.proyecto.panaderia.repository.CategoriaRepositorio;
import com.proyecto.panaderia.repository.EmpresaRepositorio;
import com.proyecto.panaderia.request.CategoriaRequest;
import com.proyecto.panaderia.services.CategoriaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaServicio {
    private final CategoriaRepositorio categoriaRepositorio;
    private final EmpresaRepositorio EmpresaRepositorio;

    @Override
    public List<Categorias> getCategoriasbyIdEmpresa(Integer idEmpresa) {
        return categoriaRepositorio.getCategoriasByEmpresa(idEmpresa);
    }

    @Override
    public Optional<Categorias> getCategoriaById(Integer id) {
        return categoriaRepositorio.findById(id)
                .map(Optional::of)
                .orElseThrow(() ->
                        new CategoriaNotFoundException("Categoria no encontrada")
                );
    }

    @Override
    public void updateCategoria(CategoriaRequest categoriaRequest, Integer id) {
        Categorias categoria =  categoriaRepositorio.findById(id).orElseThrow(()->
                new CategoriaNotFoundException("Categoria no encontrada"));
        String nombreNormalizado = categoriaRequest.getNombre().trim().toLowerCase();
        Integer empresaId = categoriaRequest.getIdEmpresa();
        Optional<Categorias> categoriaExistente = categoriaRepositorio.findByNombreIgnoreCaseAndTrimAndEmpresaIdExcludingIdAndStatusTrue(nombreNormalizado, empresaId, id);
        if (categoriaExistente.isPresent()) {
            throw new CategoriaExistException("Ya existe una categoría con ese nombre en la empresa.");
        }
        categoria.setNombre(categoriaRequest.getNombre());
        categoria.setDescripcion(categoriaRequest.getDescripcion());
        categoria.setEmpresa(EmpresaRepositorio.findById(categoriaRequest.getIdEmpresa())
                .orElseThrow(() ->
                        new EmpresaNotFoundException("Empresa no encontrada")));
        categoriaRepositorio.save(categoria);
    }

    @Override
    public void saveCategoria(CategoriaRequest categoriaRequest) {
        String nombreNormalizado = categoriaRequest.getNombre().trim().toLowerCase();
        Integer empresaId = categoriaRequest.getIdEmpresa();
        Optional<Categorias> categoriaExistente = categoriaRepositorio.findByNombreIgnoreCaseAndTrimAndEmpresaId(nombreNormalizado, empresaId);
        if (categoriaExistente.isPresent()) {
            throw new CategoriaExistException("La categoría ya existe en la empresa");
        }
        Categorias categorias = new Categorias();
        categorias.setNombre(categoriaRequest.getNombre());
        categorias.setDescripcion(categoriaRequest.getDescripcion());
        categorias.setEmpresa(EmpresaRepositorio.findById(categoriaRequest.getIdEmpresa())
                .orElseThrow(() ->
                        new EmpresaNotFoundException("Empresa no encontrada")));
        categorias.setStatus(true);
        categoriaRepositorio.save(categorias);
    }

    @Override
    public void deleteCategoria(Integer id) {
        Categorias categorias = categoriaRepositorio.findById(id)
                .orElseThrow(() ->
                        new CategoriaNotFoundException("Categoria no encontrada"));
        categorias.setStatus(false);
        categoriaRepositorio.save(categorias);

    }
}
