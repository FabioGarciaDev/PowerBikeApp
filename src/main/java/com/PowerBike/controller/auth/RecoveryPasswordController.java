package com.PowerBike.controller.auth;

import com.PowerBike.dto.ResetPasswordDTO;
import com.PowerBike.service.RecoveryPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RecoveryPasswordController {

    private final RecoveryPasswordService recoveryPasswordService;
    @PostMapping(value = "forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email){
        return new ResponseEntity(recoveryPasswordService.forgotPassword(email), HttpStatus.OK);
    }

    @PostMapping(value = "resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordDTO dto){
        return new ResponseEntity(recoveryPasswordService.resetPassword(dto), HttpStatus.OK);
    }
}
