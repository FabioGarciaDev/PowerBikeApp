package com.PowerBike.service;

import com.PowerBike.dto.MedicalRecordDto;
import com.PowerBike.entity.EBloodType;
import com.PowerBike.entity.MedicalRecord;
import com.PowerBike.entity.UserEntity;
import com.PowerBike.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final UserRepository userRepository;

    public ResponseEntity<?> saveMedicalRegister(MedicalRecordDto dto) {
        UserEntity user = userRepository.findById(dto.getIdUser()).orElse(null);
        if (user != null) {
            if (user.getMedicalRecord() == null) {
                MedicalRecord medicalRecord = MedicalRecord.builder()
                        .eps(dto.getEps())
                        .bloodType(EBloodType.valueOf(dto.getBloodType()))
                        .allergies(dto.getAllergies())
                        .diseases(dto.getDiseases())
                        .medicines(dto.getMedicines())
                        .otherMedicalConditions(dto.getOtherMedicalConditions())
                        .build();
                user.setMedicalRecord(medicalRecord);
                userRepository.save(user);
                return new ResponseEntity<>("Historial medico de " + user.getName() + " a sido creado", HttpStatus.OK);
            }else{
                MedicalRecord updateMedical = user.getMedicalRecord();
                updateMedical.setEps(dto.getEps());
                updateMedical.setAllergies(dto.getAllergies());
                updateMedical.setDiseases(dto.getDiseases());
                updateMedical.setMedicines(dto.getMedicines());
                updateMedical.setOtherMedicalConditions(dto.getOtherMedicalConditions());
                user.setMedicalRecord(updateMedical);
                userRepository.save(user);
                return new ResponseEntity<>("Historial medico de " + user.getName() + " a sido actualizado", HttpStatus.OK);
            }
        }
        return new ResponseEntity("Usuario no existe y no se puede actualizar su registro medico"
                , HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<?> getBloodTypes(){
        return new ResponseEntity(EBloodType.values(), HttpStatus.OK);
    }
    //MedicalRecord medical= medicalRecordRepository.findMedicalRegisterByUserId(dto.getIdUser());

}
