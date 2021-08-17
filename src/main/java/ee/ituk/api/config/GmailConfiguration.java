package ee.ituk.api.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Configuration
@ConditionalOnProperty(prefix = "email", value = "provider", havingValue = "gmail")
public class GmailConfiguration {

    private static final GsonFactory GSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = List.of(GmailScopes.GMAIL_SEND);
    private static final int SELECT_UNUSED_PORT = -1;
    public static final String USER = "user";

    @Bean
    public NetHttpTransport netHttpTransport() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean
    public Credential credentials(NetHttpTransport netHttpTransport) throws IOException {
        InputStream is = requireNonNull(getClass().getResourceAsStream("/credentials.json"));

        GoogleClientSecrets secrets = GoogleClientSecrets.load(GSON_FACTORY, new InputStreamReader(is));
        GoogleAuthorizationCodeFlow authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(netHttpTransport, GSON_FACTORY, secrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
                .setAccessType("offline")
                .build();

        LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(SELECT_UNUSED_PORT).build();
        return new AuthorizationCodeInstalledApp(authorizationCodeFlow, localServerReceiver).authorize(USER);
    }

    @Bean
    public Gmail gmailClient(NetHttpTransport netHttpTransport, Credential credential) {
        return new Gmail.Builder(netHttpTransport, GSON_FACTORY, credential)
                .setApplicationName("Itük")
                .build();
    }
}
