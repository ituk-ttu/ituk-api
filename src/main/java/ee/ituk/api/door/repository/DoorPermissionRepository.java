package ee.ituk.api.door.repository;

import ee.ituk.api.door.domain.DoorPermission;
import ee.ituk.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoorPermissionRepository extends JpaRepository<DoorPermission, Long> {

    List<DoorPermission> findByUser(User user);
}
