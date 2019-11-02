package ee.ituk.api.settings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Long> {

    Optional<GlobalSettings> findByName(String name);
}
