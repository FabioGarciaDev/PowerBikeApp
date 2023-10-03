package com.PowerBike.dto;

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
public class UserUpdateDto {

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

}
