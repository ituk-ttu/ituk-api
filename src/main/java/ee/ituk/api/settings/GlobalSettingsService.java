package ee.ituk.api.settings;

import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.common.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GlobalSettingsService {

    private final GlobalSettingsRepository settingsRepository;

    public int getSessionTimeInMinutes() {
        return Integer.parseInt(settingsRepository.findByName("session_duration_in_minutes").orElseThrow(
                () -> new NotFoundException(Collections.singletonList(
                        ValidationUtil.getNotFoundError(this.getClass())))).getValue());
    }
}
