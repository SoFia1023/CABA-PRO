package com.proyecto.cabapro.service;

import com.proyecto.cabapro.model.Asignacion;
import com.proyecto.cabapro.model.Partido;
import com.proyecto.cabapro.model.Arbitro;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final MessageSource messageSource;

    @Value("${spring.mail.from:Cabapro <no-reply@cabapro.local>}")
    private String from;

    public MailService(JavaMailSender mailSender, MessageSource messageSource) {
        this.mailSender = mailSender;
        this.messageSource = messageSource;
    }

    public void notificarNuevaAsignacion(Asignacion asg) {
        Arbitro a = asg.getArbitro();
        Partido p = asg.getPartido();

        if (a == null || p == null || a.getCorreo() == null || a.getCorreo().isBlank()) return;

      
        Locale locale = LocaleContextHolder.getLocale();

        // Obtener subject y body desde messages.properties
        String subject = messageSource.getMessage("mail.asignacion.subject", null, locale);

        String body = messageSource.getMessage(
                "mail.asignacion.body",
                new Object[] {
                        a.getNombre(),
                        p.getEquipoLocal(),
                        p.getEquipoVisitante(),
                        p.getFecha(),
                        p.getTorneo() != null ? p.getTorneo().getNombre() : "-",
                        asg.getMonto() != null ? asg.getMonto().setScale(2) : "0.00"
                },
                locale
        );

        enviar(a.getCorreo(), subject, body);
    }

    private void enviar(String to, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(body);
        mailSender.send(msg);
    }
}
