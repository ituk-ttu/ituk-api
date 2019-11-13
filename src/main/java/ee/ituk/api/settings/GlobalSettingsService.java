package ee.ituk.api.settings;

import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.common.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static ee.ituk.api.common.validation.ValidationUtil.getNotFoundError;

@Service
@RequiredArgsConstructor
public class GlobalSettingsService {

    private final GlobalSettingsRepository settingsRepository;

    public int getSessionTimeInMinutes() {
        return Integer.parseInt(settingsRepository.findByName(Settings.SESSION_DURATION_IN_MINUTES.getName())
                .orElseThrow(() -> new NotFoundException(Collections.singletonList(getNotFoundError(this.getClass()))))
                .getValue());
    }
}
