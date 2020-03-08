package ee.ituk.api.application.repository;

import ee.ituk.api.application.domain.Application;
import ee.ituk.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByMentor(User user);
    Optional<Application> findByUser(User user);
}
