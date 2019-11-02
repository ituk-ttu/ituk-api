package ee.ituk.api.mentor;

import ee.ituk.api.mentor.domain.MentorProfile;
import ee.ituk.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorProfileService {

    private final MentorProfileRepository mentorProfileRepository;

    public List<MentorProfile> getAllActive() {
        return mentorProfileRepository.findAll().stream()
                .filter(profile -> profile.getUser().getRole().isMentor())
                .collect(Collectors.toList());
    }

    public void create(User user) {
        MentorProfile profile = new MentorProfile(user);
        mentorProfileRepository.save(profile);
    }

}
