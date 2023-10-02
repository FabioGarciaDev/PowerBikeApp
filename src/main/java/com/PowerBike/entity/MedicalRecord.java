package com.PowerBike.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMedicalRecord;
    private String eps;
    private EBloodType bloodType;
    private String allergies;
    private String diseases;    //Enfermedades
    private String medicines;
    private String otherMedicalConditions;


}
