package ee.ituk.api.settings;

import ee.ituk.api.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GlobalSettingsService {

    private final GlobalSettingsRepository settingsRepository;

    public int getSessionTimeInMinutes() {
        return Integer.parseInt(settingsRepository.findByName("session_duration_in_minutes").orElseThrow(NotFoundException::new).getValue());
    }
}
