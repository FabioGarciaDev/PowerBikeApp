package com.PowerBike.controller;

import com.PowerBike.dto.MedicalRecordDto;
import com.PowerBike.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRegister/v1")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    @PostMapping(value = "save")
    public ResponseEntity<?> saveMedicalRegister(@RequestBody MedicalRecordDto dto){
        return medicalRecordService.saveMedicalRegister(dto);
    }

    @GetMapping(value = "allBloodTypes")
    public ResponseEntity<?> getBloodTypes() {
        return medicalRecordService.getBloodTypes();
    }

}
