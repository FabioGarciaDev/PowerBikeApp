package com.PowerBike.controller;

import com.PowerBike.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @GetMapping(value = "send")
    public ResponseEntity<?> sendEmail(){
        emailService.sendEmail();
        return  new ResponseEntity("Correo enviado con exito", HttpStatus.OK);
    }

    @GetMapping(value = "sendTemplate")
    public ResponseEntity<?> sendEmailTemplate(){
        emailService.sendEmailTemplate();
        return  new ResponseEntity("Correo con plantilla enviado con exito", HttpStatus.OK);
    }
}
