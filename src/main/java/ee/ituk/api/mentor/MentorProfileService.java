package ee.ituk.api.mentor;

import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.mentor.domain.MentorProfile;
import ee.ituk.api.user.UserRepository;
import ee.ituk.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorProfileService {

    private final MentorProfileRepository mentorProfileRepository;
    private final UserRepository userRepository;

    public void create(User user) {
        MentorProfile profile = new MentorProfile(user);
        mentorProfileRepository.save(profile);
    }

    MentorProfile getByUserId(long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        return mentorProfileRepository.findByUser(user).orElseThrow(NotFoundException::new);
    }

    MentorProfile updateMentor(MentorProfile mentorprofile) {
        User userById = userRepository.findById(mentorprofile.getUser().getId()).orElseThrow(NotFoundException::new);
        mentorprofile.setUser(userById);
        return mentorProfileRepository.save(mentorprofile);
    }

    List<MentorProfile> getAllActive() {
        return mentorProfileRepository.findAll().stream()
                .filter(profile -> profile.getUser().getRole().isCanBeMentor())
                .collect(Collectors.toList());
    }
}
