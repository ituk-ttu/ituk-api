package ee.ituk.api;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class IntegrationSmokeTest {

    final JdbcTemplate jdbcTemplate;

    @Test
    void testFlywaySetup() {
        String query = "select version from flyway_schema_history order by installed_rank asc limit 1";
        String flywayInitialVersion = jdbcTemplate.queryForObject(query, String.class);
        assertThat(flywayInitialVersion).isEqualTo("1.1");
    }
}
