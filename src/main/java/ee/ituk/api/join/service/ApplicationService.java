package ee.ituk.api.join.service;

import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.join.domain.Application;
import ee.ituk.api.join.repository.ApplicationRepository;
import ee.ituk.api.mentor.MentorProfileService;
import ee.ituk.api.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {


    private final ApplicationRepository applicationRepository;
    private final MentorProfileService mentorProfileService;
    private final UserService userService;

    public Application createApplication(Application application) {
        return saveApplication(application);
    }

    public Application findApplicationById(long id) {
        return applicationRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    private Application saveApplication(Application application) {
        application.setProcessedBy(userService.findUserById(application.getProcessedBy().getId()));
        application.setMentor(mentorProfileService.findByUser(application.getMentor().getUser()));
        return applicationRepository.save(application);
    }

    public List<Application> findAll() { return applicationRepository.findAll(); }

    public Application updateProfile(Application application) {
        return applicationRepository.save(application);
    }

    public void deleteApplication(long id) {
        Application application = findApplicationById(id);
        applicationRepository.delete(application);
    }
}
