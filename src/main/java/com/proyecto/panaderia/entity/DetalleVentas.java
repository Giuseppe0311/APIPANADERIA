package com.proyecto.panaderia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Productos productos;
    private Integer cantidad;
    private Double precio_venta;
    private Double subtotal;
    @ManyToOne
    private Ventas ventas;
}
