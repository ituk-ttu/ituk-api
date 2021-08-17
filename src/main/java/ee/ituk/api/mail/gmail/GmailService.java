package ee.ituk.api.mail.gmail;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import ee.ituk.api.application.domain.Application;
import ee.ituk.api.mail.MailService;
import ee.ituk.api.mail.Provider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import static ee.ituk.api.config.GmailConfiguration.USER;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "email", value = "provider", havingValue = "gmail")
public class GmailService implements MailService {

    private final Gmail gmail;
    private final AsyncRunner asyncRunner;
    private final VelocityEngine velocityEngine;

    @SneakyThrows
    @Override
    public CompletableFuture<Object> sendNewPasswordEmail(String email, String newRawPassword) {
        Map<String, Object> context = Map.of(
                "password", newRawPassword
        );

        Message message = message(email, "Uus salasõna", "newPassword", context);
        return sendAsync(message);
    }

    @Override
    public CompletableFuture<Object> sendNewUserPasswordEmail(String email, String newRawPassword) {
        Map<String, Object> context = Map.of(
                "password", newRawPassword
        );

        Message message = message(email, "ITÜKi liitumisavaldus on vastu võetud", "newUserPassword", context);
        return sendAsync(message);
    }

    @Override
    public CompletableFuture<Object> sendNewMinionEmail(Application application) {
        Map<String, Object> context = Map.of(
                "name", application.getFirstName() + " " + application.getLastName(),
                "email", application.getEmail()
        );

        Message message = message(application.getEmail(), "ITÜKi liitumisavaldus on vastu võetud", "newUserPassword", context);
        return sendAsync(message);
    }

    @Override
    public CompletableFuture<Object> sendJoinedEmail(Application application) {
        Map<String, Object> context = Map.of(
                "url", String.format("liitu.ituk.ee/#/%s/%s/application", application.getId(), application.getMentorSelectionCode())
        );

        Message message = message(application.getEmail(), "Tere tulemast ITÜKi!", "joined", context);
        return sendAsync(message);
    }

    @Override
    public Provider getProvider() {
        return Provider.GMAIL;
    }

    public CompletableFuture<Object> sendAsync(Message email) {
        return asyncRunner.runInAsync(() -> sendEmail(email));
    }

    @SneakyThrows
    private Message sendEmail(Message email) {
        return gmail.users().messages().send(USER, email).execute();
    }

    @SneakyThrows
    private Message message(String email, String subject, String templateName, Map<String, Object> params) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage mimeMessage = new MimeMessage(session);
        Multipart multiPart = new MimeMultipart("alternative");

        mimeMessage.setFrom(new InternetAddress("noreply@ituk.ee"));

        mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(email));
        mimeMessage.setSubject(subject);

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(renderTemplate(templateName, createContext(params), velocityEngine), "text/html; charset=utf-8");
        multiPart.addBodyPart(htmlPart);
        mimeMessage.setContent(multiPart);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        mimeMessage.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}
