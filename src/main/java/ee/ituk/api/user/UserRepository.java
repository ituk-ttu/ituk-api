package ee.ituk.api.user;

import java.util.List;
import java.util.Optional;

import ee.ituk.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);

    List<User> findByIdIn(List<Long> ids);

    List<User> findAllByOrderByIdAsc();

    List<User> findAllByArchivedOrderByIdAsc(Boolean archived);

    Optional<User> findByEmailIgnoreCase(String email);

    List<User> findAllByArchived(Boolean archived);

}
