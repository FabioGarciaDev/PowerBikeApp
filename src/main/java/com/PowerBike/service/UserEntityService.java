package com.PowerBike.service;

import com.PowerBike.entity.UserEntity;
import com.PowerBike.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserEntityService {

    private final UserRepository userRepository;

    //    Metodo para consultar todos los usuarios
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
        if (users.isEmpty()){
            return new ResponseEntity("No hay usuarios Registrados", HttpStatus.NOT_FOUND);
        }
            return new ResponseEntity(users, HttpStatus.OK);

    }

    //     Metodo para traer un user por ID
    public ResponseEntity<?> getUserById(long id) {
        Optional<UserEntity> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            UserEntity user = optional.orElse(null);
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity("Usuario no existe", HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity deleteUserById(long id) {
        Optional<UserEntity> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            UserEntity user = optional.orElse(null);
            userRepository.delete(user);
            return new ResponseEntity("Usuario " + user.getName() + " a sido eliminado con exito", HttpStatus.OK);
        }
        return new ResponseEntity("No se pudo eliminar, usuario no existe", HttpStatus.BAD_REQUEST);
    }

//    @Override
//    public User updateUser(Long id, User updatedUser) {
//        if (userRepository.existsById(id)) {
//            updatedUser.setId(id);
//            return userRepository.save(updatedUser);
//        }
//        return null;
//    }

}


