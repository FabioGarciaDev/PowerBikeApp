package com.PowerBike.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String recoveryPassword;

    @NotBlank
    private String resetPassword;
}
