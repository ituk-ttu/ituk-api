package ee.ituk.api.application.service;

import ee.ituk.api.application.domain.Application;
import ee.ituk.api.application.repository.ApplicationRepository;
import ee.ituk.api.application.validation.ApplicationValidator;
import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.mail.MailService;
import ee.ituk.api.mentor.MentorProfileRepository;
import ee.ituk.api.mentor.domain.MentorProfile;
import ee.ituk.api.user.UserService;
import ee.ituk.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static ee.ituk.api.common.validation.ValidationUtil.checkForErrors;
import static ee.ituk.api.common.validation.ValidationUtil.getNotFoundError;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final MentorProfileRepository mentorProfileRepository;
    private final UserService userService;
    private final MailService mailService;

    private final ApplicationValidator validator = new ApplicationValidator();

    public Application createApplication(Application application) {
        return saveApplication(application);
    }

    public Application findApplicationById(long id) {
        return applicationRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    public Application updateProfile(Application application) {
        return applicationRepository.save(application);
    }

    public void deleteApplication(long id) {
        Application application = findApplicationById(id);
        applicationRepository.delete(application);
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

    public void setMentorToApplication(Long applicationId, Long mentorId) {
        if (Objects.nonNull(applicationId) && Objects.nonNull(mentorId)) {
            Application application = findApplicationById(applicationId);
            MentorProfile mentorProfile = mentorProfileRepository.findById(mentorId)
                    .orElseThrow(() -> new NotFoundException(Collections.singletonList(getNotFoundError(MentorProfile.class))));
            User mentor = mentorProfile.getUser();
            if (mentor.isMentor()) {
                throw new NotFoundException(Collections.singletonList(getNotFoundError(User.class)));
            }
            application.setMentor(mentor);
            Application saved = applicationRepository.save(application);
            mailService.sendNewMinionEmail(saved);
        } else {
            throw new NotFoundException(Collections.singletonList(getNotFoundError(Application.class)));
        }
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
                        .build();
            }
        }
        return null;
    }
}
