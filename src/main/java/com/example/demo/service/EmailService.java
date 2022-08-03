package com.example.demo.service;

import com.example.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Properties;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(Customer customer) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(customer.getEmail());
        mail.setSubject("Testing Mail");
        mail.setText("Bandomas siuntimas");

        javaMailSender.send(mail);
    }

    /**
     * This fucntion is used to send mail that contains a attachment.
     *
     * @param
     * @throws MailException
     * @throws MessagingException
     */
    public void sendEmailWithAttachment(Customer customer) throws MailException, MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(customer.getEmail());
        helper.setSubject("Testing Mail API with Attachment");
        helper.setText("Please find the attached document below.");

        ClassPathResource classPathResource = new ClassPathResource("Attachment.pdf");
        helper.addAttachment(Objects.requireNonNull(classPathResource.getFilename()), classPathResource);

        javaMailSender.send(mimeMessage);
    }

//    private Session getSession() {
//        //Gmail Host
//        String host = "smtp.gmail.com";
//        String username = "tomrickus@gmail.com";
//        //Enter your Gmail password
//        String password = "";
//
//        Properties prop = new Properties();
//        prop.put("mail.smtp.auth", true);
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", host);
//        prop.put("mail.smtp.port", 587);
//        prop.put("mail.smtp.ssl.trust", host);
//
//        return Session.getInstance(prop, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//    }

}

