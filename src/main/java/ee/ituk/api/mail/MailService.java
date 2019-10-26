package ee.ituk.api.mail;


import ee.ituk.api.join.Application;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.MailRequestCallback;
import net.sargue.mailgun.Response;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    public static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final MailgunConfiguration mailgunConfig;
    private final VelocityEngine velocityEngine;


    public CompletableFuture<Response> sendNewMinionEmail(Application application) {
        VelocityContext context = createContext();
        context.put("name", application.getFirstName() + " " + application.getLastName());
        context.put("email", application.getEmail());
        return sendAsync(application.getMentor().getUser().getEmail(), "newMinion",
                context, "Uus minion");
    }

    private CompletableFuture<Response> sendAsync(String to, String templateName, VelocityContext context,
                                                  String subject) {
        log.info("Sending " + templateName + " to " + to);
        Mail mail = Mail.using(mailgunConfig)
                .to(to)
                .subject(subject)
                .text(renderTemplate("plain/" + templateName, context))
                .build();
        CompletableFuture<Response> result = sendAsync(mail);
        result.whenComplete((response, ex) -> {
            if (ex != null) {
                log.warn("Sending " + templateName + " to " + to + " FAIL", ex);
            } else if (!response.isOk()) {
                log.warn("Sending " + templateName + " to " + to + " FAIL: " + response.responseMessage());
            } else {
                log.info("Sending " + templateName + " to " + to + " SUCCESS: " + response.responseMessage());
            }
        });

        return result;
    }

    private CompletableFuture<Response> sendAsync(Mail mail) {
        CompletableFuture<Response> future = new CompletableFuture<>();
        mail.sendAsync(new MailRequestCallback() {
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

    private String renderTemplate(String templateName, VelocityContext context) {
        StringWriter writer = new StringWriter();
        try {
            velocityEngine.getTemplate("emailTemplates/" + templateName + ".vm").merge(context, writer);
            return writer.getBuffer().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private VelocityContext createContext() {
        VelocityContext context = new VelocityContext();
        context.put("datePattern", DATE_PATTERN);
        return context;
    }
}

