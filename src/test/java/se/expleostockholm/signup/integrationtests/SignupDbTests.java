package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class SignupDbTests {

    @Container
    protected static final SignupDbTestcontainer dbTestContainer = SignupDbTestcontainer.getInstance();


    @Test
    @Order(0)
    void verifyThatTestDbIsRunning() {
        assertTrue(dbTestContainer.isRunning());
    }

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + dbTestContainer.getJdbcUrl(),
                    "spring.datasource.username=" + dbTestContainer.getUsername(),
                    "spring.datasource.password=" + dbTestContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
