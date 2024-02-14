package com.proyecto.panaderia.entity;

import jakarta.persistence.*;
import jdk.jfr.Timespan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ventas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Sucursales sucursal;
    @ManyToOne
    private Usuarios usuario;
    private String tipo_pago;
    private String tipo_comprobante;
    private String num_comprobante;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha = new Date(); // Inicializa con la fecha y hora actuales
    private Double total;
    private String status;

}
