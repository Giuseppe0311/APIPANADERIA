package com.proyecto.panaderia.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosRequest {
private Integer id;
    private String nombre;
    private String descripcion;
    private MultipartFile imagen;
    private Double precioBase;
    private Integer stock;
    private boolean estado;
    private Integer categoria;
    private Integer empresa;

}
