package com.PowerBike.auth;

import com.PowerBike.entity.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String name;

    private String lastname;

    private String secondLastname;

    @NotBlank
    @Size(max = 30)
    private String documentType;

    @NotBlank
    @Size(max = 30)
    private String documentNumber;

    @Email
    @NotBlank
    @Size(max = 80)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String gender;

    @NotBlank
    @Size(max = 80)
    private String phoneNumber;

    @NotBlank
    @Size(max = 80)
    private String address;

    @NotBlank
    private String city;

    private String dateOfBirth;
    /*//Con este metodo debo parsear la fecha de nacimiento ingresado por el usuario
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);*/

    private String idCard;

    private ERole role;
}
