package com.example.demo.service;

import com.example.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(User user) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("tomrickus@gmail.com");
        mail.setSubject("Testing Mail");
        mail.setText("Bandomas siuntimas");

        javaMailSender.send(mail);
    }

    private final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    public void sendMailWithAttachment(String toEmail,
                                       String body,
                                       String subject,
                                       String attachment
    ) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("tomrickus@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
        mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), fileSystemResource);
        javaMailSender.send(mimeMessage);

        LOGGER.info("Mail with attachment sent successfully!");
    }
}

