package com.proyecto.panaderia.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalleCompras {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @ManyToOne
        private Compras compra;
        private  String producto;
        private int cantidad;
        private double precio;
        private double subtotal;


}
