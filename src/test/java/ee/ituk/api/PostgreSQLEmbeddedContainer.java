package ee.ituk.api;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLEmbeddedContainer extends PostgreSQLContainer<PostgreSQLEmbeddedContainer> {
    public static final PostgreSQLEmbeddedContainer DB_CONTAINER = new PostgreSQLEmbeddedContainer();

    private PostgreSQLEmbeddedContainer() {
        super("postgres:11.5-alpine");
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            DB_CONTAINER.start();

            TestPropertyValues.of(
                    "spring.datasource.url=" + DB_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + DB_CONTAINER.getUsername(),
                    "spring.datasource.password=" + DB_CONTAINER.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
