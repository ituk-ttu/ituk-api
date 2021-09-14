package ee.ituk.api.user.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN,
    BOARD,
    MEMBER;

    static final List<Role> admins = List.of(ADMIN, BOARD);

    public boolean isAdmin() {
        return admins.contains(this);
    }
}
