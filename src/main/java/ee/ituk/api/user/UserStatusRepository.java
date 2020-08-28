package ee.ituk.api.user;

import ee.ituk.api.user.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {

    UserStatus getByStatusName(String statusName);
}
