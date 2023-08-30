package com.PowerBike.auth;


import com.PowerBike.dto.ResetPasswordDTO;
import com.PowerBike.service.RecoveryPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RecoveryPasswordService recoveryPasswordService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login (@RequestBody LoginDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(value = "forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email){
        return new ResponseEntity(recoveryPasswordService.forgotPassword(email), HttpStatus.OK);
    }

    @PostMapping(value = "resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO dto){
        return new ResponseEntity(recoveryPasswordService.resetPassword(dto), HttpStatus.OK);
    }
}
