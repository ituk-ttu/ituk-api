package ee.ituk.api.user;

import ee.ituk.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);

    List<User> findByIdIn(List<Long> ids);

    List<User> findAllByOrderByIdAsc();

    Optional<User> findByEmailIgnoreCase(String email);

    List<User> findAllByArchived(Boolean archived);

}
