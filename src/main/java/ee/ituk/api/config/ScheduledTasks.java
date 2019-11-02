package ee.ituk.api.config;

import ee.ituk.api.login.SessionService;
import ee.ituk.api.login.domain.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

    private final SessionService sessionService;

    @Scheduled(cron = "0 * * * * *")
    public void checkSessions() {
        log.info("Checking sessions");
        LocalDateTime now = LocalDateTime.now();
        List<Session> sessionsToDelete = sessionService.findAll().stream()
                .filter(session -> Duration.between(session.getCreatedAt(), now).toMinutes() > 60)
                .collect(Collectors.toList());
        sessionsToDelete.forEach(
                session -> log.info("Deleting session for " + session.getUser().getEmail()
                        + " started at " + session.getCreatedAt().toString()));
        sessionService.deleteSessions(sessionsToDelete);
    }
}
