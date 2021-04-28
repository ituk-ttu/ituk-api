package ee.ituk.api.user;

import ee.ituk.api.application.domain.Application;
import ee.ituk.api.application.repository.ApplicationRepository;
import ee.ituk.api.common.exception.BadCredentialsException;
import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.common.exception.ValidationException;
import ee.ituk.api.common.validation.personal.IdNumber;
import ee.ituk.api.login.SessionService;
import ee.ituk.api.mentor.MentorProfileRepository;
import ee.ituk.api.mentor.MentorProfileService;
import ee.ituk.api.user.domain.Role;
import ee.ituk.api.user.domain.User;
import ee.ituk.api.user.dto.MentorNameDto;
import ee.ituk.api.user.dto.PasswordChangeDto;
import ee.ituk.api.user.dto.UserBirthdayDto;
import ee.ituk.api.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ee.ituk.api.common.validation.ValidationUtil.checkForErrors;
import static ee.ituk.api.common.validation.ValidationUtil.getNotFoundError;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MentorProfileRepository mentorProfileRepository;
    private final ApplicationRepository applicationRepository;
    private final MentorProfileService mentorProfileService;
    private final SessionService sessionService;
    private final BCryptPasswordEncoder encoder;

    private final UserValidator userValidator = new UserValidator();

    @Override
    public UserDetails loadUserByUsername(String email) {
        return loadInternalUserByUsername(email);
    }

    public User loadInternalUserByUsername(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(BadCredentialsException::new);
    }

    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new NotFoundException(Collections.singletonList(getNotFoundError(this.getClass()))));
    }

    public List<User> findByIds(List<Long> ids) {
        return userRepository.findByIdIn(ids);
    }

    void changePassword(long id, PasswordChangeDto passwordChangeDto) {
        User user = findUserById(id);
        if (encoder.matches(passwordChangeDto.getOldPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(passwordChangeDto.getNewPassword()));
            checkForErrors(userValidator.validatePasswordChange(passwordChangeDto));
            saveUser(user);
        } else {
            throw new BadCredentialsException();
        }
    }

    public List<User> findAll(Boolean showArchived) {
        if (showArchived) {
            return userRepository.findAllByOrderByIdAsc();
        }
        return userRepository.findAllByArchivedOrderByIdAsc(false);
    }

    public List<User> findAllByArchived(Boolean archived) {
        return userRepository.findAllByArchived(archived);
    }

    @Transactional
    public User createUser(User user) {
        checkForErrors(userValidator.validate(user));
        User savedUser = saveUser(user);
        if (savedUser.isMentor() && mentorProfileRepository.findByUser(savedUser).isEmpty()) {
            mentorProfileService.create(savedUser);
        }
        return savedUser;
    }

    void logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = loadInternalUserByUsername(username);
        sessionService.deleteSession(user);
    }

    long getMemberCount() {
        return userRepository.count();
    }

    @Transactional
    User updateUser(User user) {
        checkForErrors(userValidator.validate(user));
        User fromBase = userRepository.getOne(user.getId());
        user.setPassword(fromBase.getPassword());
        if (user.isMentor() && mentorProfileRepository.findByUser(user).isEmpty()) {
            mentorProfileService.create(user);
        }
        return userRepository.save(user);
    }

    User getLoggedUser() {
        return userRepository.findByEmailIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new NotFoundException(Collections.singletonList(getNotFoundError(this.getClass()))));
    }

    void changeRole(Long id, Role role) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setRole(role);
        userRepository.save(user);
        if (user.isMentor() && mentorProfileRepository.findByUser(user).isEmpty()) {
            mentorProfileService.create(user);
        }
    }

    MentorNameDto getMentorName(Long userId) {
        User user = findUserById(userId);
        Optional<Application> applicationOptional = applicationRepository.findByUser(user);
        if (applicationOptional.isPresent()) {
            return MentorNameDto.builder().name(applicationOptional.get().getMentor().getFullName()).build();
        }
        return MentorNameDto.builder().name("").build();
    }

    List<String> getTodayBirthdayUserNames() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(this::isBirthdayToday)
                .map(User::getFullName)
                .collect(Collectors.toList());
    }

    List<UserBirthdayDto> getLastWeekBirthdays() {
        List<User> users = userRepository.findAll().stream()
                .filter(user -> !user.isArchived())
                .filter(user -> !userValidator.validatePersonalCode(user).hasErrors())
                .collect(Collectors.toList());
        LocalDate currentDate = LocalDate.now();
        final LocalDate yesterday = currentDate.minusDays(1);
        final LocalDate nextWeek = currentDate.plusDays(8);

        List<UserBirthdayDto> birthdays = new ArrayList<>();
        users.forEach(user -> {
            IdNumber idNumber = new IdNumber(user);
            int month = idNumber.getMonthNumber();
            int day = idNumber.getDayNumber();
            LocalDate birthday = LocalDate.of(currentDate.getYear(), month, day);
            if (birthday.isAfter(yesterday) && birthday.isBefore(nextWeek)) {
                birthdays.add(UserBirthdayDto.builder()
                        .fullName(user.getFullName())
                        .birthday(birthday)
                        .build());
            }
        });
        birthdays.sort(Comparator.comparing(UserBirthdayDto::getBirthday));
        return birthdays;
    }

    void archive(Long id, boolean isArchived) {
        User user = userRepository.findById(id).orElseThrow(() -> new ValidationException(getNotFoundError(User.class)));
        user.setArchived(isArchived);
        userRepository.save(user);
    }

    private User saveUser(User user) {
        return userRepository.save(user);
    }


    private boolean isBirthdayToday(User user) {
        if (user.getPersonalCode() != null) {
            int month = Integer.parseInt(user.getPersonalCode().substring(3, 5));
            int day = Integer.parseInt(user.getPersonalCode().substring(5, 7));
            return LocalDate.now().getMonthValue() == month && LocalDate.now().getDayOfMonth() == day;
        }
        return false;
    }
}
