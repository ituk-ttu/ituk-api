package ee.ituk.api.join.service;

import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.join.domain.Application;
import ee.ituk.api.join.repository.ApplicationRepository;
import ee.ituk.api.mail.MailService;
import ee.ituk.api.user.UserService;
import ee.ituk.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static ee.ituk.api.common.validation.ValidationUtil.getNotFoundError;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final MailService mailService;

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
        application.setProcessedBy(userService.findUserById(application.getProcessedBy().getId()));
        application.setMentor(application.getMentor());
        return applicationRepository.save(application);
    }

    public void setMentorToApplication(Long applicationId, Long mentorId) {
        if (Objects.nonNull(applicationId) && Objects.nonNull(mentorId)) {
            Application application = findApplicationById(applicationId);
            User mentor = userService.findUserById(mentorId);
            if (!mentor.getRole().isCanBeMentor()) {
                throw new NotFoundException(Collections.singletonList(getNotFoundError(User.class)));
            }
            application.setMentor(mentor);
            Application saved = applicationRepository.save(application);
            mailService.sendNewMinionEmail(saved);
        } else {
            throw new NotFoundException(Collections.singletonList(getNotFoundError(Application.class)));
        }
    }
}
