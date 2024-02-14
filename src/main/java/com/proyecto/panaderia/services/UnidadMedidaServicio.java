package com.proyecto.panaderia.services;

import com.proyecto.panaderia.entity.UnidadesMedida;
import com.proyecto.panaderia.request.UnidadMedidaRequest;

import java.util.List;

public interface UnidadMedidaServicio {
    List<UnidadesMedida> getUnidadesMedida();
    List<UnidadesMedida> getUnidadesMedidaByEmpesa(Integer id);
    UnidadesMedida getUnidadMedidaById(Integer id);
    void saveUnidadMedida(UnidadMedidaRequest unidadMedidaRequest);
    void updateUnidadMedida(UnidadMedidaRequest unidadMedidaRequest,Integer id);
    void deleteUnidadMedidaById(Integer id);
}
