package com.proyecto.panaderia.controller.admin;

import com.proyecto.panaderia.dto.UsuarioAdminSucusalDTO;
import com.proyecto.panaderia.request.RegistroRequest;
import com.proyecto.panaderia.services.UserServiceForSucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/usuarios")
public class UsuariosForSucursalController {
    private final UserServiceForSucursal userServiceForSucursal;
    @GetMapping
    public ResponseEntity<?> getUser(
            @RequestParam(required = false) Integer idEmpresa,
            @RequestParam(required = false) Integer idusuario
    ) {
        if (idEmpresa != null) {
            return ResponseEntity.ok(userServiceForSucursal.getAllUserOfEmpresaId(idEmpresa));
        } else if (idusuario != null) {
            return ResponseEntity.ok(userServiceForSucursal.getUsuarioByid(idusuario));
        }
        return ResponseEntity.ok(Map.of("mensaje", "No se ha seleccionado ninguna opción"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody RegistroRequest registroRequest, @PathVariable Integer id) {
        userServiceForSucursal.actualizar(registroRequest, id);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario actualizado correctamente"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userServiceForSucursal.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado correctamente"));
    }
}
