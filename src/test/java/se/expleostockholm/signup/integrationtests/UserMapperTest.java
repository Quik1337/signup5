package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.repository.UserMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class UserMapperTest extends SignupDbTests {

    @Resource
    private UserMapper userMapper;

    @Test
    @Order(1)
    void getUserById() {
        Optional<User> user = userMapper.getUserById(1L);

        assertAll(
                () -> assertTrue(user.isPresent(), "No user found!"),
                () -> assertEquals(1L, user.get().getId(), "User id did not match!"),
                () -> assertEquals("Ali", user.get().getFirst_name(), "First name did not match!"),
                () -> assertEquals("Matys", user.get().getLast_name(), "Last name did not match!"),
                () -> assertEquals("amatys0@wp.com", user.get().getEmail(), "Email did not match!")
        );
    }

    @Test
    @Order(2)
    void getAllUsers() {
        List<User> allUsers = userMapper.getAllUsers();
        assertEquals(50, allUsers.size(), "Number of users did not match!");
    }
}