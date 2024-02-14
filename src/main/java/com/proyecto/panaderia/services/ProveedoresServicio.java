package com.proyecto.panaderia.services;

import com.proyecto.panaderia.entity.Proveedores;
import com.proyecto.panaderia.request.ProveedoresRequest;

import java.util.List;

public interface ProveedoresServicio {
   List<Proveedores> getProveedoresBySucursal(Integer id);
    Proveedores getProveedorById(Integer id);
    void saveProveedor(ProveedoresRequest proveedoresRequest);
    void  updateProveedor(Integer id ,ProveedoresRequest proveedoresRequest);
    void deleteProveedor(Integer id);
}
