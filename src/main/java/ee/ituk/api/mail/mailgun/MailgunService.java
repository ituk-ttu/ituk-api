package ee.ituk.api.mail.mailgun;


import ee.ituk.api.application.domain.Application;
import ee.ituk.api.mail.MailService;
import ee.ituk.api.mail.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.MailRequestCallback;
import net.sargue.mailgun.Response;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = "email", value = "provider", havingValue = "gmail")
public class MailgunService implements MailService {

    private final MailgunConfiguration mailgunConfig;
    private final VelocityEngine velocityEngine;

    @Override
    public CompletableFuture<Object> sendNewPasswordEmail(String email, String newRawPassword) {
        Map<String, Object> context = Map.of(
                "password", newRawPassword
        );

        return sendAsync(email, "newPassword", context, "Uus salasõna");
    }

    @Override
    public CompletableFuture<Object> sendNewUserPasswordEmail(String email, String newRawPassword) {
        Map<String, Object> context = Map.of(
                "password", newRawPassword
        );

        return sendAsync(email, "newUserPassword", context, "ITÜKi liitumisavaldus on vastu võetud");
    }

    @Override
    public CompletableFuture<Object> sendNewMinionEmail(Application application) {
        Map<String, Object> context = Map.of(
                "name", application.getFirstName() + " " + application.getLastName(),
                "email", application.getEmail()
        );

        return sendAsync(application.getMentor().getEmail(), "newMinion", context, "Uus minion");
    }

    @Override
    public CompletableFuture<Object> sendJoinedEmail(Application application) {
        Map<String, Object> context = Map.of(
                "url", String.format("liitu.ituk.ee/#/%s/%s/application", application.getId(), application.getMentorSelectionCode())
        );

        return sendAsync(application.getEmail(), "joined", context, "Tere tulemast ITÜKi!");
    }

    @Override
    public Provider getProvider() {
        return Provider.MAILGUN;
    }

    private CompletableFuture<Object> sendAsync(String to, String templateName, Map<String, Object> context, String subject) {
        log.info("Sending " + templateName + " to " + to);
        VelocityContext velocityContext = createContext(context);
        Mail mail = Mail.using(mailgunConfig)
                .to(to)
                .subject(subject)
                .html(renderTemplate("html/" + templateName, velocityContext, velocityEngine))
                .build();
        CompletableFuture<Object> result = sendAsync(mail);
        result.whenComplete((response, ex) -> {
            if (ex != null) {
                log.warn("Sending " + templateName + " to " + to + " FAIL", ex);
            }
            Response mailgunResponse = (Response) response;
            if (!mailgunResponse.isOk()) {
                log.warn("Sending " + templateName + " to " + to + " FAIL: " + mailgunResponse.responseMessage());
            } else {
                log.info("Sending " + templateName + " to " + to + " SUCCESS: " + mailgunResponse.responseMessage());
            }
        });

        return result;
    }

    private CompletableFuture<Object> sendAsync(Mail email) {
        CompletableFuture<Object> future = new CompletableFuture<>();
        email.sendAsync(new MailRequestCallback() {
            @Override
            public void completed(Response response) {
                future.complete(response);
            }

            @Override
            public void failed(Throwable throwable) {
                future.completeExceptionally(throwable);
            }
        });
        return future;
    }
}

