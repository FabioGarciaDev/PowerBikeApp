package com.PowerBike;

import com.PowerBike.entity.ERole;
import com.PowerBike.entity.UserEntity;
import com.PowerBike.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootApplication
public class PowerBikeApiRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerBikeApiRestApplication.class, args);

    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Bean
    CommandLineRunner init() {
        return args -> {
            // El método run se ejecutará automáticamente al iniciar la aplicación
            String emailFabito = "fabinho1080@hotmail.com";

            if (userRepository.existsByEmail(emailFabito)) {
                System.out.println("el usuario admin existe");
            }else {
                UserEntity userEntity = UserEntity.builder()
                        .name("Fabito")
                        .lastname("Garcia")
                        .secondLastname("User")
                        .documentType("CC")
                        .documentNumber("1019023233")
                        .email(emailFabito)
                        .password(passwordEncoder.encode("1234"))  // Corregir aquí
                        .gender("Male")
                        .phoneNumber("1234567")
                        .address("calle falsa")
                        .city("Bogota")
                        .dateOfBirth(LocalDate.now())
                        .idCard(null)
                        .updateDate(LocalDateTime.now())
                        .activeUser(true)
                        .role(ERole.ADMIN)
                        .build();

                userRepository.save(userEntity);
            }
        };
    }
}
