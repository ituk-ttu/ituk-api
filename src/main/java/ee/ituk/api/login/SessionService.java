package ee.ituk.api.login;

import ee.ituk.api.common.GlobalUtil;
import ee.ituk.api.login.domain.Session;
import ee.ituk.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public boolean checkSession(User user, String code) {
        return sessionRepository.existsSessionByUserAndCode(user, code);
    }

    public void deleteSession(User user) {
        sessionRepository.deleteByUser(user);
    }

    public Session createSession(User user) {
        Session session = sessionRepository.findByUser(user).orElse(new Session());
        session.setUser(user);
        session.setCode(GlobalUtil.generateCode());
        session.setCreatedAt(LocalDateTime.now());
        return sessionRepository.save(session);
    }


}
