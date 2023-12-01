package com.PowerBike.controller;

import com.PowerBike.dto.MedicalRecordDto;
import com.PowerBike.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/medicalRegister")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    @PostMapping(value = "save")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<?> saveMedicalRegister(@RequestBody MedicalRecordDto dto){
        return medicalRecordService.saveMedicalRegister(dto);
    }

    @GetMapping(value = "allBloodTypes")
    public ResponseEntity<?> getBloodTypes() {
        return medicalRecordService.getBloodTypes();
    }

}
