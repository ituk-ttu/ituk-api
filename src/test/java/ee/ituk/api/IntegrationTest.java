package ee.ituk.api;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @implSpec concrete test classes <b>must not</b> use {@link org.junit.jupiter.api.Nested @Nested} annotations.
 * Because some functionality configured here will loose its effects
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApiApplicationTests.class)
@ContextConfiguration(initializers = PostgreSQLEmbeddedContainer.Initializer.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Commit
@ClearDbBeforeTestMethod
@WithMockUser
public @interface IntegrationTest {
}
