package ee.ituk.api.repository;

import ee.ituk.api.dto.User;
import ee.ituk.api.dto.UserInput;
import ee.ituk.tables.pojos.Mentor;
import ee.ituk.tables.pojos.Userstatus;
import ee.ituk.tables.records.UserRecord;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ee.ituk.tables.Mentor.MENTOR;
import static ee.ituk.tables.User.USER;
import static ee.ituk.tables.Userstatus.USERSTATUS;

@Component
public class UserRepository {

    @Autowired
    private DSLContext dsl;

    public User getUser(int id) {
        return getUserSelect()
                .where(USER.ID.eq(id))
                .fetchAny(userRecordMapper);
    }

    public List<User> getUsersByStatus(Integer statusId) {
        return getUserSelect()
                .where(USER.STATUSID.eq(statusId))
                .fetch(userRecordMapper);
    }

    public List<User> getActiveUsers() {
        return getUserSelect()
                .where(USER.ARCHIVED.eq(false))
                .fetch(userRecordMapper);
    }

    public List<User> getAllUsers() {
        return getUserSelect()
                .fetch(userRecordMapper);
    }

    private RecordMapper<Record, User> userRecordMapper = record -> {
        UserRecord userRecord = record.into(USER);
        User user = userRecord.into(User.class);
        user.setUserstatus(record.into(USERSTATUS).into(Userstatus.class));
        user.setMentor(record.into(MENTOR).into(Mentor.class));
        return user;
    };

    private SelectOnConditionStep<Record> getUserSelect() {
        return dsl.select()
                .from(USER)
                .join(USERSTATUS)
                .on(USERSTATUS.STATUSID.eq(USER.STATUSID))
                .leftJoin(MENTOR)
                .on(MENTOR.USERID.eq(USER.MENTORID))
                ;
    }

    public User addUser(UserInput user) {
        UserRecord userRecord = dsl.newRecord(USER, user);
//                .with(USER.STATUSID, Optional.of(user)
//                    .map(User::getUserstatus)
//                    .map(Userstatus::getStatusid)
//                    .orElse(null))
//                .with(USER.MENTORID, Optional.of(user)
//                    .map(User::getMentor)
//                    .map(ee.ituk.tables.pojos.Mentor::getUserid)
//                    .orElse(null));
        userRecord.store();
        return getUser(userRecord.getId());
    }

    public User changeStatus(int id, int statusId) {
        dsl.update(USER)
                .set(USER.STATUSID, statusId)
                .where(USER.ID.eq(id))
                .execute();
        return getUser(id);
    }

    public User updateUser(UserInput userInput) {
        UserRecord userRecord = dsl.newRecord(USER, userInput);
        List<Field<?>> notNullfields = Arrays.stream(userRecord.fields())
                .filter(field -> field.get(userRecord) != null)
                .collect(Collectors.toList());
        userRecord.update(notNullfields);
        return getUser(userRecord.getId());
    }
}
