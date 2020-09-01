package ee.ituk.api.application.service;

import ee.ituk.api.application.domain.Application;
import ee.ituk.api.application.dto.ApplicationStatus;
import ee.ituk.api.application.dto.ChangeApplicationStatusRequest;
import ee.ituk.api.application.repository.ApplicationRepository;
import ee.ituk.api.application.validation.ApplicationValidator;
import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.common.exception.ValidationException;
import ee.ituk.api.common.validation.ValidationUtil;
import ee.ituk.api.mail.MailService;
import ee.ituk.api.mentor.MentorProfileMapper;
import ee.ituk.api.mentor.MentorProfileRepository;
import ee.ituk.api.mentor.domain.MentorProfile;
import ee.ituk.api.mentor.dto.MentorProfileDto;
import ee.ituk.api.recovery.RecoveryService;
import ee.ituk.api.user.UserService;
import ee.ituk.api.user.UserStatusRepository;
import ee.ituk.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static ee.ituk.api.common.validation.ValidationUtil.MENTOR_NOT_ACTIVE;
import static ee.ituk.api.common.validation.ValidationUtil.checkForErrors;
import static ee.ituk.api.common.validation.ValidationUtil.getNotFoundError;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final MentorProfileRepository mentorProfileRepository;
    private final UserService userService;
    private final MailService mailService;
    private final RecoveryService recoveryService;
    private final UserStatusRepository userStatusRepository;
    private final BCryptPasswordEncoder encoder;

    private final ApplicationValidator validator = new ApplicationValidator();
    private final MentorProfileMapper mentorProfileMapper = Mappers.getMapper(MentorProfileMapper.class);

    public Application createApplication(Application application) {
        final Application saved = saveApplication(application);
        this.mailService.sendJoinedEmail(application);
        return saved;
    }

    public Application findApplicationById(long id) {
        return applicationRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Application> findAll() {
        return applicationRepository.findAllByOrderByCreatedAtDesc();
    }

    public Application updateApplication(Application application) {
        return applicationRepository.save(application);
    }

    public void deleteApplication(long id) {
        Application application = findApplicationById(id);
        applicationRepository.delete(application);
    }

    public void setMentorToApplication(Long applicationId, Long mentorId) {
        Application application = findApplicationById(applicationId);
        MentorProfile mentorProfile = mentorProfileRepository.findById(mentorId)
                .orElseThrow(() -> new NotFoundException(Collections.singletonList(getNotFoundError(MentorProfile.class))));
        User mentor = mentorProfile.getUser();
        if (!mentor.isMentor()) {
            throw new NotFoundException(Collections.singletonList(getNotFoundError(User.class)));
        }
        if (!mentorProfile.isEnabled()) {
            throw new ValidationException(new ErrorMessage(MENTOR_NOT_ACTIVE, "Mentor not active anymore, choose different one"));
        }
        application.setMentor(mentor);
        Application saved = applicationRepository.save(application);
        mailService.sendNewMinionEmail(saved);
    }

    public List<Application> findByMentor(Long mentorUserId) {
        User mentor = userService.findUserById(mentorUserId);
        return applicationRepository.findAllByMentor(mentor);
    }

    public ApplicationsMentor apply(Long applicationId, String selectionCode) {
        Optional<Application> maybeApplication = applicationRepository.findById(applicationId);
        if (maybeApplication.isPresent()) {
            Application application = maybeApplication.get();

            if (application.getMentorSelectionCode().equals(selectionCode)) {
                return ApplicationsMentor.builder()
                        .name(application.getFirstName())
                        .applicationId(applicationId)
                        .mentor(getMentorProfileByApplication(application))
                        .build();
            }
        }
        return null;
    }

    public void changeApplicationStatus(Long applicationId, ChangeApplicationStatusRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException(Collections.singletonList(getNotFoundError(Application.class))));
        application.setStatus(request.getStatus().name());

        if (request.getStatus() == ApplicationStatus.ACCEPTED) {
            final String rawPassword = UUID.randomUUID().toString().replace("-", "");
            User user = User.builder()
                    .email(application.getEmail())
                    .curriculum(application.getCurriculum())
                    .firstName(application.getFirstName())
                    .lastName(application.getLastName())
                    .password(encoder.encode(rawPassword))
                    .personalCode(application.getPersonalCode())
                    .studentCode(application.getStudentCode())
                    .status(userStatusRepository.getByStatusName("Noorliige"))
                    .build();

            final User savedUser = userService.createUser(user);

            mailService.sendNewUserPasswordEmail(savedUser.getEmail(), rawPassword);

            application.setUser(savedUser);
        }
        applicationRepository.save(application);

    }

    private Application saveApplication(Application application) {
        checkForErrors(validator.validateOnCreate(application));
        application.setCreatedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());
        application.setMentorSelectionCode(UUID.randomUUID().toString().replace("-", ""));
        if (application.getMentor() == null || application.getMentor().getId() == null) {
            application.setMentor(null);
        }
        if (application.getProcessedBy() == null || application.getProcessedBy().getId() == null) {
            application.setProcessedBy(null);
        }
        return applicationRepository.save(application);
    }

    private MentorProfileDto getMentorProfileByApplication(Application application) {
        if (application.getMentor() == null || application.getMentor().getId() == null) {
            return null;
        }

        MentorProfile mentorProfile = mentorProfileRepository.findByUser(application.getMentor()).orElse(null);
        return mentorProfileMapper.mentorProfileToDto(mentorProfile);
    }
}
