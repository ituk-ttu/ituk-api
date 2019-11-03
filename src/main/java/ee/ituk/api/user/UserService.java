package ee.ituk.api.user;

import ee.ituk.api.common.exception.BadCredentialsException;
import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.common.validation.ValidationUtil;
import ee.ituk.api.login.SessionService;
import ee.ituk.api.mentor.MentorProfileRepository;
import ee.ituk.api.mentor.MentorProfileService;
import ee.ituk.api.recovery.RecoveryKey;
import ee.ituk.api.recovery.RecoveryService;
import ee.ituk.api.user.domain.Role;
import ee.ituk.api.user.domain.User;
import ee.ituk.api.user.dto.PasswordChangeDto;
import ee.ituk.api.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.checkForErrors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MentorProfileRepository mentorProfileRepository;
    private final RecoveryService recoveryService;
    private final MentorProfileService mentorProfileService;
    private final SessionService sessionService;
    private final UserValidator userValidator = new UserValidator();

    @Override
    public UserDetails loadUserByUsername(String email) {
        return loadInternalUserByUsername(email);
    }

    public User loadInternalUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(BadCredentialsException::new);
    }

    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new NotFoundException(Collections.singletonList(ValidationUtil.getNotFoundError(this.getClass()))));
    }

    void changePassword(long id, PasswordChangeDto passwordChangeDto) {
        User user = findUserById(id);
        checkForErrors(userValidator.validatePasswordChange(user, passwordChangeDto));
        user.setPassword(passwordChangeDto.getNewPassword());
        saveUser(user);
    }

    List<User> findAll() {
        return userRepository.findAll();
    }

    User createUser(User user) {

        checkForErrors(userValidator.validateOnCreate(user));

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

    void logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = loadInternalUserByUsername(username);
        sessionService.deleteSession(user);
    }

    public long getMemberCount() {
        return userRepository.count();
    }

    public User updateUser(User user) {
        //TODO validation
        return userRepository.save(user);
    }

    public User getLoggedUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new NotFoundException(Collections.singletonList(ValidationUtil.getNotFoundError(this.getClass()))));
    }

    public void changeRole(Long id, Role role) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setRole(role);
        if (role.isMentor() && mentorProfileRepository.findByUser(user).isEmpty()) {
            mentorProfileService.create(user);
        }
    }

}
