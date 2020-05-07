package se.expleostockholm.signup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Invitation;

@Service
public class InvitationEmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public InvitationEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(Invitation invitation) throws MailException {
        SimpleMailMessage email = new SimpleMailMessage();

        String emailLink = "https://tsuzumic.github.io/signup5_lia/events/" + invitation.getEvent().getId() + "/" + invitation.getId();

        email.setTo(invitation.getGuest().getEmail());
        email.setFrom("signup5lia@gmail.com");
        email.setSubject("SignUp5 - Event invitation");
        email.setText(emailLink);

        javaMailSender.send(email);
    }
}
