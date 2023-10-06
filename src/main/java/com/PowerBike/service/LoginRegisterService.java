package com.PowerBike.service;

import com.PowerBike.dto.AuthResponseDto;
import com.PowerBike.dto.LoginDto;
import com.PowerBike.dto.RegisterDto;
import com.PowerBike.entity.ERole;
import com.PowerBike.entity.UserEntity;
import com.PowerBike.repository.UserRepository;
import com.PowerBike.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class LoginRegisterService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto login(LoginDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername()
                ,request.getPassword()));
        UserDetails user=userRepository.findByEmail(request.getUsername()).orElseThrow();
        UserEntity userEntity = userRepository.findByEmail(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponseDto.builder()
                .message("Inicio de sesion correcto")
                .name(userEntity.getName())
                .userId(userEntity.getIdUser())
                .role(String.valueOf(userEntity.getRole()))
                .token(token)
                .build();
    }

    //Metodo para registar un usuario y se retorna datos para front
    public AuthResponseDto register(RegisterDto request) {
        if (userRepository.existsByEmail(request.getEmail())){
            return AuthResponseDto.builder()
                    .message("El email "+request.getEmail()+" ya esta registrado")
                    .build();
        }

        //Capturo fecha String y la convierto a LocalDate para almacenar el usuario
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(request.getDateOfBirth(), formatter);

        //Construccion del user para almacenar en la BD
        UserEntity userEntity = UserEntity.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .secondLastname(request.getSecondLastname())
                .documentType(request.getDocumentType())
                .documentNumber(request.getDocumentNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .city(request.getCity())
                .dateOfBirth(dateOfBirth)
                .idCard(request.getIdCard())
                .updateDate(LocalDateTime.now())
                .activeUser(true)
                .role(ERole.USER)
                .build();

        userRepository.save(userEntity);

        return AuthResponseDto.builder()
                .message("Usuario Registrado con exito")
                .name(userEntity.getName())
                .userId(userEntity.getIdUser())
                .role(String.valueOf(userEntity.getRole()))
                .token(jwtService.getToken(userEntity))
                .build();
    }
}
