package ee.ituk.api.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Modifying
    @Query(value = "update feedback set deleted_at = now() where id = ?1", nativeQuery = true)
    void delete(Long id);
}
