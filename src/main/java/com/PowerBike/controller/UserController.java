package com.PowerBike.controller;

import com.PowerBike.dto.UserUpdateDto;
import com.PowerBike.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;

    // Obtener todos los usuarios
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return userEntityService.getAllUsers();
    }

    // Obtener un usuario por id
   @GetMapping("/get/{id}")
   @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        return userEntityService.getUserById(id);
    }

    // Operación DELETE - Eliminar un usuario por id
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        return userEntityService.deleteUserById(id);
    }

    // Operación UPDATE - Actualizar un usuario por ID
    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserUpdateDto dto){
        return userEntityService.updateUser(id, dto);
    }

    //Activar o desactivar user
    @PutMapping("activeDesactive/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> activeDesactiveProduct(@PathVariable long id){
        return userEntityService.activeDesactiveUser(id);
    }

}
