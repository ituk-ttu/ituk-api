package ee.ituk.api.user;

import ee.ituk.api.exception.NotFoundException;
import ee.ituk.api.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
