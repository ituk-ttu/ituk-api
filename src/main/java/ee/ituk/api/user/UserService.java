package ee.ituk.api.user;

import ee.ituk.api.exception.EmailNotFoundException;
import ee.ituk.api.exception.NotFoundException;
import ee.ituk.api.exception.ValidationException;
import ee.ituk.api.recovery.RecoveryKey;
import ee.ituk.api.recovery.RecoveryService;
import ee.ituk.api.user.domain.User;
import ee.ituk.api.user.dto.PasswordChangeDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RecoveryService recoveryService;


    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
    }

    User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    void changePassword(long id, PasswordChangeDto passwordChangeDto) {
        User user = findUserById(id);
        if (!user.getPassword().equals(passwordChangeDto.getOldPassword())) {
            throw new ValidationException();
        }
        user.setPassword(passwordChangeDto.getNewPassword());
        saveUser(user);
    }

    List<User> findAll() {
        return userRepository.findAll();
    }

    User createUser(User user) {
        //TODO add validation module here

        user = saveUser(user);

        RecoveryKey recoveryKey = recoveryService.createRecoveryKey(user);

        //TODO SEND MAIL

        return user;
    }

    User getRecoveryUser(String key) {
        return recoveryService.getUserByKey(key);
    }

    private User saveUser(User user) {
        return userRepository.save(user);
    }
}
