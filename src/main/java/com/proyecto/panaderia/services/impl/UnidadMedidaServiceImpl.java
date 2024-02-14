package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.entity.UnidadesMedida;
import com.proyecto.panaderia.exceptions.UnidadExistException;
import com.proyecto.panaderia.repository.EmpresaRepositorio;
import com.proyecto.panaderia.repository.UnidadMedidaRepositorio;
import com.proyecto.panaderia.request.UnidadMedidaRequest;
import com.proyecto.panaderia.services.UnidadMedidaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnidadMedidaServiceImpl implements UnidadMedidaServicio {
    private final UnidadMedidaRepositorio unidadMedidaRepositorio;
    private final EmpresaRepositorio empresaRepositorio;
    @Override
    public List<UnidadesMedida> getUnidadesMedida() {
        return unidadMedidaRepositorio.getUnidadMedida();
    }

    @Override
    public List<UnidadesMedida> getUnidadesMedidaByEmpesa(Integer id) {
        return unidadMedidaRepositorio.getUnidadMedidaByEmpresa(id);
    }

    @Override
    public UnidadesMedida getUnidadMedidaById(Integer id) {
         return unidadMedidaRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada"));
    }

    @Override
    public void saveUnidadMedida(UnidadMedidaRequest unidadMedidaRequest) {
        String nombreNormalizado = unidadMedidaRequest.getNombre().trim().toLowerCase();
        Optional<UnidadesMedida> unidadMedidaExistente = unidadMedidaRepositorio.findByNombreAndEmpresaId(nombreNormalizado, unidadMedidaRequest.getIdempresa());

        if (unidadMedidaExistente.isPresent()) {
            throw new UnidadExistException("Ya existe una unidad de medida con ese nombre en la empresa.");
        }
        UnidadesMedida unidadesMedida = new UnidadesMedida();
        unidadesMedida.setNombre(unidadMedidaRequest.getNombre());
        unidadesMedida.setEmpresa(empresaRepositorio.findById(unidadMedidaRequest.getIdempresa()).orElseThrow(() -> new RuntimeException("Empresa no encontrada")));
        unidadesMedida.setStatus(true);
        unidadMedidaRepositorio.save(unidadesMedida);

    }

    @Override
    public void updateUnidadMedida(UnidadMedidaRequest unidadMedidaRequest, Integer id) {
        String nombreNormalizado = unidadMedidaRequest.getNombre().trim().toLowerCase();
        Optional<UnidadesMedida> unidadMedidaExistente = unidadMedidaRepositorio.findByNombreAndEmpresaIdExcludingId(nombreNormalizado, unidadMedidaRequest.getIdempresa(), id);
        if (unidadMedidaExistente.isPresent()) {
            throw new UnidadExistException("Ya existe otra unidad de medida con el mismo nombre en la empresa.");
        }

        UnidadesMedida unidadesMedida = unidadMedidaRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada"));
        unidadesMedida.setNombre(unidadMedidaRequest.getNombre());
        unidadesMedida.setEmpresa(empresaRepositorio.findById(unidadMedidaRequest.getIdempresa()).orElseThrow(() -> new RuntimeException("Empresa no encontrada")));
        unidadMedidaRepositorio.save(unidadesMedida);
    }

    @Override
    public void deleteUnidadMedidaById(Integer id) {
        UnidadesMedida unidadesMedida = unidadMedidaRepositorio.findById(id).orElseThrow(()-> new RuntimeException("Unidad de medida no encontrada"));
        unidadesMedida.setStatus(false);
        unidadMedidaRepositorio.save(unidadesMedida);
    }
}
