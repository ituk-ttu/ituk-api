package ee.ituk.api.mail;

import ee.ituk.api.application.domain.Application;
import lombok.SneakyThrows;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface MailService {

    DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    CompletableFuture<Object> sendNewPasswordEmail(String email, String newRawPassword);

    CompletableFuture<Object> sendNewUserPasswordEmail(String email, String newRawPassword);

    CompletableFuture<Object> sendNewMinionEmail(Application application);

    CompletableFuture<Object> sendJoinedEmail(Application application);

    Provider getProvider();

    default VelocityContext createContext(Map<String, Object> templateParameters) {
        VelocityContext context = new VelocityContext();
        context.put("datePattern", DATE_PATTERN);
        templateParameters.forEach(context::put);
        return context;
    }

    @SneakyThrows
    default String renderTemplate(String templateName, VelocityContext context, VelocityEngine velocityEngine) {
        StringWriter writer = new StringWriter();
        velocityEngine.getTemplate("emailTemplates/" + templateName + ".vm", "UTF-8").merge(context, writer);
        return writer.getBuffer().toString();
    }

}
