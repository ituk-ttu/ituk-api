package ee.ituk.api.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(true),
    BOARD(true),
    MEMBER(false),
    MENTOR(true);

    private final boolean canBeMentor;

}
