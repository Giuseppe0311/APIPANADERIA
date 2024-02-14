package com.proyecto.panaderia.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagosRequest {
    private Integer idcompra;
    private String tipo_pago;
    private String monto_pagado;
    private Date fechadepago;

}
