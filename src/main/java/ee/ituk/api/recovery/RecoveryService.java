package ee.ituk.api.recovery;

import ee.ituk.api.mail.MailService;
import ee.ituk.api.user.PasswordRecoveryRepository;
import ee.ituk.api.user.domain.PasswordRecovery;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RecoveryService {
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final BCryptPasswordEncoder encoder;
    private final MailService mailService;

    public void sendNewRecoveryPassword(String email) {
        Optional<PasswordRecovery> byEmail = passwordRecoveryRepository.findByEmail(email);
        byEmail.ifPresent(passwordRecoveryRepository::delete);

        String rawPassword = UUID.randomUUID().toString().replace("-", "");

        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setEmail(email);
        passwordRecovery.setPassword(encoder.encode(rawPassword));
        passwordRecoveryRepository.save(passwordRecovery);
        mailService.sendNewPasswordEmail(email, rawPassword);
    }

    public Optional<String> getRecoveryPasswordByEmail(String email) {
        Optional<PasswordRecovery> byEmail = passwordRecoveryRepository.findByEmail(email);
        return byEmail.map(PasswordRecovery::getPassword);
    }

}
