package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.entity.Empresas;
import com.proyecto.panaderia.exceptions.EmpresaExistException;
import com.proyecto.panaderia.exceptions.EmpresaNotFoundException;
import com.proyecto.panaderia.repository.EmpresaRepositorio;
import com.proyecto.panaderia.request.EmpresaRequest;
import com.proyecto.panaderia.services.EmpresaServicio;
import com.proyecto.panaderia.utils.cloudinary.CloudService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaServicioImpl implements EmpresaServicio {
    private final EmpresaRepositorio empresaRepositorio;
    private final CloudService cloudService;

    @Override
    public List<Empresas> getEmpresas() {
        return empresaRepositorio.findAll();
    }

    @Override
    public Optional<Empresas> getEmpresaById(Integer id) {
        return empresaRepositorio.findById(id)
                .map(Optional::of)
                .orElseThrow(() ->
                        new EmpresaNotFoundException("No se encontro la empresa")
                );
    }

    @Override
    @Transactional
    public void updateEmpresa(EmpresaRequest empresaRequest,Integer id) {
        Empresas empresa = empresaRepositorio.findById(id)
                .orElseThrow(() ->
                        new EmpresaNotFoundException("No se encontro la empresa")
                );
        String nombreNormalizado = empresaRequest.getNombre().trim().toLowerCase();
        String direccionNormalizada = empresaRequest.getDireccion().trim().toLowerCase();
        String telefonoNormalizado = empresaRequest.getTelefono().replace(" ", "");
        empresaRepositorio.findByNombreOrDireccionOrTelefonoIgnoreCaseAndTrimExcludingId(nombreNormalizado, direccionNormalizada, telefonoNormalizado, id).ifPresent(empresa1 -> {
            throw new EmpresaExistException("Ya existe otra empresa con el mismo nombre, dirección o teléfono.");
        });
        empresa.setNombre(empresaRequest.getNombre());
        empresa.setInformacion(empresaRequest.getInformacion());
        empresa.setDireccion(empresaRequest.getDireccion());
        empresa.setTelefono(empresaRequest.getTelefono());
        empresa.setLogo((empresaRequest.getLogo() != null && !empresaRequest.getLogo().isEmpty()
                ? procesaLogo(empresaRequest.getLogo())
                : empresa.getLogo()));
        empresaRepositorio.save(empresa);
    }

    @Override
    public void saveEmpresa(EmpresaRequest empresaRequest) {
        String nombreNormalizado = empresaRequest.getNombre().trim().toLowerCase();
        String direccionNormalizada = empresaRequest.getDireccion().trim().toLowerCase();
        String telefonoNormalizado = empresaRequest.getTelefono().replace(" ", "");
        // Verificar si ya existe una empresa con los mismos datos
        empresaRepositorio.findByNombreOrDireccionOrTelefonoIgnoreCaseAndTrim(nombreNormalizado, direccionNormalizada, telefonoNormalizado).ifPresent(empresa -> {
            throw new EmpresaExistException("Ya existe una empresa con el mismo nombre, dirección o teléfono.");
        });

        Empresas empresa = new Empresas();
        empresa.setNombre(empresaRequest.getNombre());
        empresa.setInformacion(empresaRequest.getInformacion());
        empresa.setDireccion(empresaRequest.getDireccion());
        empresa.setTelefono(empresaRequest.getTelefono());
        if (empresaRequest.getLogo() != null && !empresaRequest.getLogo().isEmpty()) {
            empresa.setLogo(procesaLogo(empresaRequest.getLogo()));
        }else{
            empresa.setLogo("");
        }
        empresa.setStatus(true);
        empresaRepositorio.save(empresa);
    }

    @Override
    public void deleteEmpresaById(Integer id) {
        Empresas empresa = empresaRepositorio.findById(id)
                .orElseThrow(() ->
                        new EmpresaNotFoundException("No se encontro la empresa")
                );
        empresa.setStatus(false);
        empresaRepositorio.save(empresa);
    }
    private String procesaLogo(MultipartFile logo) {
        return cloudService.uploadimg(logo);
    }
}
