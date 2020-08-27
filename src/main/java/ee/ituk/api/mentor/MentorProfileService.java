package ee.ituk.api.mentor;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.common.exception.ValidationException;
import ee.ituk.api.common.filestorage.FileStorageService;
import ee.ituk.api.common.validation.ValidationUtil;
import ee.ituk.api.mentor.domain.MentorProfile;
import ee.ituk.api.user.UserRepository;
import ee.ituk.api.user.domain.Role;
import ee.ituk.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ee.ituk.api.common.validation.ValidationUtil.INVALID_ROLE;

@Service
@RequiredArgsConstructor
public class MentorProfileService {

    private final MentorProfileRepository mentorProfileRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public void create(User user) {
        MentorProfile profile = new MentorProfile(user);
        mentorProfileRepository.save(profile);
    }

    MentorProfile getByMentorId(long id) {
        return mentorProfileRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    MentorProfile getByUserId(long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        return mentorProfileRepository.findByUser(user).orElseThrow(NotFoundException::new);
    }

    MentorProfile updateMentor(MentorProfile mentorprofile) {
        User userById = userRepository.findById(mentorprofile.getUser().getId()).orElseThrow(NotFoundException::new);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (loggedInUser.getRole() == Role.MEMBER && !loggedInUser.getId().equals(userById.getId())) {
            throw new ValidationException(ErrorMessage.builder()
                    .code(INVALID_ROLE)
                    .build());
        }
        mentorprofile.setUser(userById);
        mentorprofile.setPicture(mentorProfileRepository.findById(mentorprofile.getId())
                .map(MentorProfile::getPicture).orElseThrow(NotFoundException::new));
        return mentorProfileRepository.save(mentorprofile);
    }

    List<MentorProfile> getAllActive() {
        return mentorProfileRepository.findAll().stream()
                .filter(profile -> profile.getUser().isMentor())
                .filter(MentorProfile::isEnabled)
                .collect(Collectors.toList());
    }

    List<MentorProfile> getAll() {
        return mentorProfileRepository.findAll();
    }

    public void uploadPicture(String id, MultipartFile file) {
        this.fileStorageService.uploadMentorPicture(id, file);
    }

    public void setNewBase64ProfileImage(Long id, String base64) {
        this.mentorProfileRepository.updateMentorProfilePic(id, base64);
    }

    public Resource loadPicture(String id) {
       return this.fileStorageService.loadMentorPicture(id);
    }

    public String loadPicture(Long id) {
        final Optional<MentorProfile> byId = mentorProfileRepository.findById(id);
        return byId.map(MentorProfile::getPicture).orElse(null);
    }
}
