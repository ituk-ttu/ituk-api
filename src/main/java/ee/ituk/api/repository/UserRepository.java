package ee.ituk.api.repository;

import ee.ituk.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static ee.ituk.tables.User.USER;

@Component
public class UserRepository {

    @Autowired
    private DSLContext dsl;

    public User getUser(int id) {
        return dsl.select()
                .from(USER)
                .where(USER.ID.eq(id))
                .fetchAnyInto(User.class);
    }

    public List<User> getUsersByStatus(Integer statusId) {
        return dsl.select()
                .from(USER)
                .where(USER.STATUS.eq(statusId))
                .fetchInto(User.class);
    }

    public List<User> getActiveUsers() {
        return dsl.select()
                .from(USER)
                .where(USER.ARCHIVED.eq(false))
                .fetchInto(User.class);
    }

    public List<User> getAllUsers() {
        return dsl.selectFrom(USER)
                .fetchInto(User.class);
    }

}
