package ee.ituk.api.recovery;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecoveryKeyRepository extends CrudRepository<RecoveryKey, Long> {

    Optional<RecoveryKey> findByKey(String key);
}
