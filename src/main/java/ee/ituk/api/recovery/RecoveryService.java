package ee.ituk.api.recovery;

import ee.ituk.api.common.GlobalUtil;
import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.common.validation.ValidationUtil;
import ee.ituk.api.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class RecoveryService {

    private final RecoveryKeyRepository recoveryKeyRepository;

    public User getUserByKey(String key) {
        return recoveryKeyRepository.findByKey(key)
                .map(RecoveryKey::getUser)
                .orElseThrow(() -> new NotFoundException(Collections.singletonList(ValidationUtil.getNotFoundError(this.getClass()))));
    }

    public RecoveryKey createRecoveryKey(User user) {
        RecoveryKey recoveryKey = new RecoveryKey();
        recoveryKey.setUser(user);
        recoveryKey.setKey(GlobalUtil.generateCode());
        return recoveryKeyRepository.save(recoveryKey);
    }
}
