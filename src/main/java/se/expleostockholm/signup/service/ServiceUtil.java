package se.expleostockholm.signup.service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ServiceUtil {

    public static boolean isValidEmail(String email) {

        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        }  catch (AddressException ex) {
            return false;
         }
    }
}
