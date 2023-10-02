package com.PowerBike.dto;

import com.PowerBike.entity.EBloodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDto {

    private Long idUser;
    private String eps;
    private String bloodType;
    private String allergies;
    private String diseases;    //Enfermedades
    private String medicines;
    private String otherMedicalConditions;


}
