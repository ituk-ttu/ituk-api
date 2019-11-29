package ee.ituk.api.door.repository;

import ee.ituk.api.door.domain.Door;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoorRepository extends JpaRepository<Door, String> {

    boolean existsByCode(String code);
}
