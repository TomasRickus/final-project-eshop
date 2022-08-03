package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
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

    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);    @Autowired
    private EmailService notificationService;

    @Autowired
    private Customer customer;

    /**
     *
     * @return
     */
    @RequestMapping("send-mail")
    public String send() {

        /*
         * Creating a User with the help of User class that we have declared and setting
         * Email address of the sender.
         */
        customer.setEmail("tomrickus@gmail.com");  //Receiver's email address
        /*
         * Here we will call sendEmail() for Sending mail to the sender.
         */
        try {
            notificationService.sendEmail(customer);
        } catch (MailException mailException) {
            LOGGER.info(String.valueOf(mailException));
        }
        return "Congratulations! Your mail has been send to the user.";
    }

    /**
     *
     * @return
     * @throws MessagingException
     */
    @RequestMapping("send-mail-attachment")
    public String sendWithAttachment() throws MessagingException {

        /*
         * Creating a User with the help of User class that we have declared and setting
         * Email address of the sender.
         */
        customer.setEmail("tomrickus@gmail.com"); //Receiver's email address

        /*
         * Here we will call sendEmailWithAttachment() for Sending mail to the sender
         * that contains a attachment.
         */
        try {
            notificationService.sendEmailWithAttachment(customer);
        } catch (MailException mailException) {
            LOGGER.info(String.valueOf(mailException));
        }
        return "Congratulations! Your mail has been send to the user.";
    }
}