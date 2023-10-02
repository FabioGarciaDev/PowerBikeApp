package com.PowerBike.controller;

import com.PowerBike.entity.UserEntity;
import com.PowerBike.repository.UserRepository;
import com.PowerBike.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;

    // Obtener todos los usuarios
    @GetMapping("/allUsers")
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
   /* @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if (userRepository.existsById(id)) {
            updatedUser.setId(id); // Asegura que el ID coincida
            User savedUser = userRepository.save(updatedUser);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
}
