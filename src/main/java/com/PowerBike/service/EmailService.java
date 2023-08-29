package com.PowerBike.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {


    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("powerbike096@gmail.com");
        message.setTo("fabiogarcia2588@gmail.com");
        message.setSubject("Email de prueba");
        message.setText("Este es contenido del mail");

        javaMailSender.send(message);
    }

    public void sendEmailTemplate() {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            String htmlText = templateEngine.process("recoveryEmailTemplate", context);
            helper.setFrom("powerbike096@gmail.com");
            helper.setTo("fabiogarcia2588@gmail.com");
            helper.setSubject("Email de prueba template");
            helper.setText(htmlText, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
