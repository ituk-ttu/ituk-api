package ee.ituk.api.door.repository;

import ee.ituk.api.door.domain.DoorPermissionLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoorPermissionLogEntryRepository extends JpaRepository<DoorPermissionLogEntry, Long> {
}
