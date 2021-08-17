package ee.ituk.api.mail.mailgun;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;


@ConfigurationProperties("email")
@ConstructorBinding
public class MailgunConfig {

    private final String domain;
    private final String apiKey;
    private final String fromName;
    private final String fromEmail;

    public MailgunConfig(String domain, String apiKey, String fromName, String fromEmail) {
        this.domain = domain;
        this.apiKey = apiKey;
        this.fromName = fromName;
        this.fromEmail = fromEmail;
    }

    @Bean
    public MailgunConfiguration getMailgunConfig() {
        return (MailgunConfiguration) new MailgunConfiguration().domain(domain)
                .apiKey(apiKey)
                .from(fromName, fromEmail)
                .apiUrl("https://api.mailgun.net/v3");
    }

    @Bean
    public VelocityEngine getVelocityEngine() {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();
        return engine;
    }

}
