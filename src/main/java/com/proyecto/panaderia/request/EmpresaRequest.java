package com.proyecto.panaderia.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaRequest {
    private String nombre;
    private String informacion;
    private String direccion;
    private String telefono;
    private MultipartFile logo;
}
