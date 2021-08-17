package ee.ituk.api;

import ee.ituk.api.mail.mailgun.MailgunConfiguration;
import ee.ituk.api.mail.mailgun.MailgunService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootApplication
public class ApiApplicationTests {

    @MockBean
    private MailgunService mailgunService;
}

