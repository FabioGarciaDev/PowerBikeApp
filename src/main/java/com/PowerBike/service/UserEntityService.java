package com.PowerBike.service;

import com.PowerBike.dto.UserUpdateDto;
import com.PowerBike.entity.UserEntity;
import com.PowerBike.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserEntityService {

    private final UserRepository userRepository;

    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>("No hay usuarios registrados", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<?> getUserById(long id) {
        Optional<UserEntity> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            UserEntity user = optional.orElse(null);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("Usuario no existe", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> updateUser(long id, UserUpdateDto dto) {
        if (userRepository.existsById(id)) {
            UserEntity user = userRepository.findById(id).orElseThrow();
            user.setGender(dto.getGender());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setAddress(dto.getAddress());
            user.setCity(dto.getCity());
            userRepository.save(user);
            return new ResponseEntity<>("El usuario " + user.getName() + " a actualizado correctamente su información"
                    , HttpStatus.OK);
        }
        return new ResponseEntity<>("El usuario no existe", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> deleteUserById(long id) {
        Optional<UserEntity> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            UserEntity user = optional.orElse(null);
            userRepository.delete(user);
            return new ResponseEntity<>("Usuario " + user.getName() + " a sido eliminado con exito", HttpStatus.OK);
        }
        return new ResponseEntity<>("No se pudo eliminar, usuario no existe", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> existUser(long id) {
        return new ResponseEntity<>(userRepository.existsById(id), HttpStatus.OK);
    }

}


