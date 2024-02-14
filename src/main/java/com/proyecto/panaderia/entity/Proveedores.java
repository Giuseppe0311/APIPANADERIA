package com.proyecto.panaderia.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proveedores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String RUC;
    private String email;
    @ManyToOne
    private Sucursales sucursal;
    private boolean estado;
}
