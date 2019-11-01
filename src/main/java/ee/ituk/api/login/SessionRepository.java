package ee.ituk.api.login;

import ee.ituk.api.login.domain.Session;
import ee.ituk.api.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {

    boolean existsByUser(User user);

    void deleteByUser(User user);
}
