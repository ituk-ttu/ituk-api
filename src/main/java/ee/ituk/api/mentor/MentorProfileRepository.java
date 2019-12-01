package ee.ituk.api.mentor;

import ee.ituk.api.mentor.domain.MentorProfile;
import ee.ituk.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MentorProfileRepository extends JpaRepository<MentorProfile, Long> {

    public Optional<MentorProfile> findByUser(User user);

    @Modifying
    @Transactional
    @Query(value = "update MentorProfile mentor set picture = :base64 where mentor.user.id=:userId")
    public void updateMentorProfilePic(Long userId, String base64);

}
