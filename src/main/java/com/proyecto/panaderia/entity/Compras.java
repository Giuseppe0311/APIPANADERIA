package com.proyecto.panaderia.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
        private Date fecha;
    private String tipo_comprobante;
    private String num_comprobante;
    @ManyToOne
    private Proveedores proveedor;
    private Double total;
    @ManyToOne
    private Sucursales sucursal;
    private String tipo_pago; // CONTADO O CREDITO
    private String estado_pago; // PAGADO O PENDIENTE

}
