package com.proyecto.panaderia.controller.superAdmin;

import com.proyecto.panaderia.request.RegistroRequest;
import com.proyecto.panaderia.services.UserServiceforAdminEmpresa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/superadmin/usuarios")
@RequiredArgsConstructor
public class UsuariosForEmpresasController {
    private final UserServiceforAdminEmpresa userServiceforAdminEmpresa;

    @GetMapping
    public ResponseEntity<?> getUser(
            @RequestParam(required = false) boolean byEmpresa,
            @RequestParam(required = false) Integer idusuario
    ) {
        if (byEmpresa) {
            return ResponseEntity.ok(userServiceforAdminEmpresa.getAllUserOfEmpresas());
        } else if (idusuario != null) {
            return ResponseEntity.ok(userServiceforAdminEmpresa.getUsuarioByid(idusuario));
        }
        return ResponseEntity.ok(Map.of("mensaje", "No se ha seleccionado ninguna opción"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody RegistroRequest registroRequest, @PathVariable Integer id) {
        userServiceforAdminEmpresa.actualizar(registroRequest, id);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario actualizado correctamente"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userServiceforAdminEmpresa.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado correctamente"));
    }
}
