package ee.ituk.api.join.repository;

import ee.ituk.api.join.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
