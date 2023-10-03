package com.PowerBike.controller;

import com.PowerBike.dto.UserUpdateDto;
import com.PowerBike.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;

    // Obtener todos los usuarios
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        return userEntityService.getAllUsers();
    }

    // Obtener un usuario por id
   @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        return userEntityService.getUserById(id);
    }

    // Operación DELETE - Eliminar un usuario por id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        return userEntityService.deleteUserById(id);
    }

    // Operación UPDATE - Actualizar un usuario por ID
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserUpdateDto dto){
        return userEntityService.updateUser(id, dto);
    }

    @GetMapping("prueba/{id}")
    public ResponseEntity<?> existUser(@PathVariable long id) {
        return userEntityService.existUser(id);
    }

}
