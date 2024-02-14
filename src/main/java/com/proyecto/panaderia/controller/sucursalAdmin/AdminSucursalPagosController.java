package com.proyecto.panaderia.controller.sucursalAdmin;

import com.proyecto.panaderia.dto.PagosDTO;
import com.proyecto.panaderia.mapper.PagosDTOMapper;
import com.proyecto.panaderia.request.PagosRequest;
import com.proyecto.panaderia.services.PagosServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adminsucursal/pagos")
public class AdminSucursalPagosController {
    private final PagosServicio pagosServicio;
    private final PagosDTOMapper pagosDTOMapper;

    @GetMapping
    public ResponseEntity<?> listarPagosbySucursal(
            @RequestParam(required = false) Integer idsucursal
    ){
        if(idsucursal != null){
            List<PagosDTO> pagosDTOS = pagosServicio.listarPagosPorSucursal(idsucursal)
                    .stream()
                    .map(pagosDTOMapper)
                    .toList();
            return ResponseEntity.ok().body(pagosDTOS);
        }
        return ResponseEntity.badRequest().body(Map.of("mensaje", "No se ha enviado el id de la sucursal"));
    }
    @PostMapping
    public ResponseEntity<?> guardarPago(@RequestBody PagosRequest pagosRequest){
            pagosServicio.guardarPago(pagosRequest);
        return ResponseEntity.ok().body(Map.of("mensaje", "Pago guardado correctamente"));
    }

}
