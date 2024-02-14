package com.proyecto.panaderia.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosRequest {
    private String nombre;
    private String descripcion;
    private Double precioBase;
    private Integer stock;
    private String idcategoria;
    private String idempresa;
    private String idunidadMedida;
    private MultipartFile imagen;

}
