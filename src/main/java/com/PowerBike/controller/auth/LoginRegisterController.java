package com.PowerBike.controller.auth;


import com.PowerBike.dto.AuthResponseDto;
import com.PowerBike.dto.LoginDto;
import com.PowerBike.dto.RegisterDto;
import com.PowerBike.service.LoginRegisterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginRegisterController {

    private final LoginRegisterService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponseDto> login (@RequestBody @Valid LoginDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponseDto> registerUser(@RequestBody @Valid RegisterDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }

}
