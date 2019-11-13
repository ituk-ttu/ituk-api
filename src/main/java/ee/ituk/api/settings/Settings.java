package ee.ituk.api.settings;

import lombok.Getter;

@Getter
public enum Settings {

    SESSION_DURATION_IN_MINUTES("session_duration_in_minutes");

    private String name;

    Settings(String name) {
        this.name = name;
    }
}
