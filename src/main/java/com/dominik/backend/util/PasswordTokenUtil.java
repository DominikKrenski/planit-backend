package com.dominik.backend.util;

import com.dominik.backend.entity.PasswordToken;
import com.dominik.backend.entity.PlanitUser;
import com.dominik.backend.service.PasswordTokenService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by dominik on 05.06.2017.
 */

@Component
public class PasswordTokenUtil {

    public static PasswordToken generateToken(PlanitUser user) {

        String token = UUID.randomUUID().toString();

        PasswordToken passwordToken = new PasswordToken(token, user);

        return passwordToken;

    }

    public static String generateResetLink(PasswordToken token) {

        String link = "planit-backend.com:8888/api/user/update-password?token=" + token.getToken() + "&id=" + token.getUser().getId();

        return link;
    }

    @Async
    public static void sendEmail(JavaMailSender mailSender, String link, String email) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject("Password reset token");
        mailMessage.setFrom("admin@planit.com");
        mailMessage.setText(link);

        mailSender.send(mailMessage);
    }

    public static boolean validateToken(PasswordToken token, Long id) {

        if (token == null || token.getUser().getId() != id)
            return false;

        return true;
    }

    /*public static boolean validateToken(String token, Long id, PasswordTokenService tokenService) {

        // Pobranie tokena z bazy danych
        PasswordToken passwordToken = tokenService.findToken(token);

        if (passwordToken == null || passwordToken.getUser().getId() != id)
            return false;

        return true;
    }*/
}
