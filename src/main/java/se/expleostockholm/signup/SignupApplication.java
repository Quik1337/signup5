package se.expleostockholm.signup;

import net.fortuna.ical4j.data.ParserException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static se.expleostockholm.signup.service.EmailService.*;


@SpringBootApplication
public class SignupApplication {

    public static void main(String[] args) throws MessagingException, IOException, ParserException {
        SpringApplication.run(SignupApplication.class, args);

        List<String> emails = new ArrayList<>();
        emails.add("fake_email@domain.com");

        Message message = createEmail(emails, createCalendar());

//        sendEmail(message);

    }

}
