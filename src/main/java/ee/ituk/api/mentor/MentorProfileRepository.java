package ee.ituk.api.mentor;

import ee.ituk.api.mentor.domain.MentorProfile;
import ee.ituk.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MentorProfileRepository extends JpaRepository<MentorProfile, Long> {

    public Optional<MentorProfile> findByUser(User user);
}
