package com.PowerBike.dto;

import com.PowerBike.entity.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    @NotBlank
    private String name;

    @NotBlank
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

    @NotBlank
    private String dateOfBirth;

    private String idCard;

    private ERole role;
}
