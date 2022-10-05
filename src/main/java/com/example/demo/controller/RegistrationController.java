package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class RegistrationController {

    public static final String MAIL_SEND_TO_USER = "Congratulations! Your mail has been send to the user.";
    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    @Autowired
    private EmailService notificationService;

    @Autowired
    private User user;

    @RequestMapping("send-mail")
    public String send() {

        user.setEmail("tomrickus@gmail.com");  //Receiver's email address

        try {
            notificationService.sendEmail(user);
        } catch (MailException mailException) {
            LOGGER.info(String.valueOf(mailException));
        }
        return MAIL_SEND_TO_USER;
    }

    @Autowired
    private EmailService emailService;

    @RequestMapping("send-mail-attachment")

    public String sendMailWithAttachment() throws MessagingException {
        emailService.sendMailWithAttachment("tomrickus@gmail.com",
                "This is body",
                "This Email this attachment",
                "products.pdf");

        try {
            notificationService.sendEmail(user);
        } catch (MailException mailException) {
            LOGGER.info(String.valueOf(mailException));
        }
        return MAIL_SEND_TO_USER;

    }
}