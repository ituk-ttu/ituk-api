package ee.ituk.api.user;

import ee.ituk.api.user.domain.PasswordRecovery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, Long>{

    Optional<PasswordRecovery> findByEmail(String email);
}
