package com.PowerBike.service;

import com.PowerBike.entity.UserEntity;
import com.PowerBike.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecoveryPasswordService {

    @Value("${mail.urlFrontRecoveryPassword}")
    private String urlFront;
    @Value("${spring.mail.username}")
    private  String mailFrom;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;

    public String forgotPassword(String email){
        if (!userRepository.existsByEmail(email)){
            return "El usuario no existe";
        }
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow();
        UUID uuid = UUID.randomUUID();
        userEntity.setRecoveryPassword(uuid.toString());
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            Map<String, Object> variablesTemplate = new HashMap<>();
            variablesTemplate.put("username",userEntity.getName());
            variablesTemplate.put("emailUsername",userEntity.getEmail());
            variablesTemplate.put("urlFront",urlFront);
            variablesTemplate.put("recoveryPassword",userEntity.getRecoveryPassword());
            context.setVariables(variablesTemplate);
            String htmlText = templateEngine.process("recoveryPasswordTemplate", context);
            helper.setFrom(mailFrom);
            helper.setTo(userEntity.getEmail());
            helper.setSubject("Recuperacion de contraseña PowerBike");
            helper.setText(htmlText, true);

            userRepository.save(userEntity);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Correo de recuperacion enviado con exito";
    }

}
