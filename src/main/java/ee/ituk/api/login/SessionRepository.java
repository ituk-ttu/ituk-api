package ee.ituk.api.login;

import ee.ituk.api.login.domain.Session;
import ee.ituk.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByUser(User user);

    boolean existsByUser(User user);

    boolean existsSessionByUserAndCode(User user, String code);

    void deleteByUser(User user);
}
