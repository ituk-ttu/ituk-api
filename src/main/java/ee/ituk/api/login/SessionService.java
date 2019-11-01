package ee.ituk.api.login;

import ee.ituk.api.login.domain.Session;
import ee.ituk.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public boolean checkSession(User user) {
        return sessionRepository.existsByUser(user);
    }

    public void deleteSession(User user) {
        sessionRepository.deleteByUser(user);
    }

    public Session createSession(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setCreatedAt(LocalDateTime.now());
        return sessionRepository.save(session);
    }


}
