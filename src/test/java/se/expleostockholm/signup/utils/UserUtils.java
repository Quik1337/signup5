package se.expleostockholm.signup.utils;

import com.github.javafaker.Faker;
import se.expleostockholm.signup.domain.User;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserUtils {

    public static User createMockUser() {
        Faker faker = new Faker((new Locale("sv-SE")));
        String randomFirstName = faker.name().firstName();
        String randomLastName = faker.name().lastName();
        String regex = "[^a-zA-Z]+";
        String randomEmail = randomFirstName.replaceAll(regex, "") + "." + randomLastName.replaceAll(regex, "") + "@mail.com";
        return User.builder()
                .first_name(randomFirstName)
                .last_name(randomLastName)
                .email(randomEmail.toLowerCase())
                .build();
    }

    public static void assertUsersAreEqual(User expectedUser, User actualUser, String role) {
        assertAll(
                () -> assertEquals(expectedUser.getEmail(), actualUser.getEmail(), role + " email did not match!"),
                () -> assertEquals(expectedUser.getFirst_name(), actualUser.getFirst_name(), role + " first name did not match!"),
                () -> assertEquals(expectedUser.getLast_name(), actualUser.getLast_name(), role + " last name did not match!")
        );
    }
}