package com.proyecto.panaderia.exceptions;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaNotFoundException extends RuntimeException{
    private String message;
}
