package ee.ituk.api.repository;

import ee.ituk.api.dto.User;
import ee.ituk.tables.Mentor;
import ee.ituk.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SelectOnConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
        user.setUserstatus(record.into(USERSTATUS).into(ee.ituk.tables.pojos.Userstatus.class));
        user.setMentor(record.into(MENTOR).into(ee.ituk.tables.pojos.Mentor.class));
        return user;
    };

    private SelectOnConditionStep<Record> getUserSelect() {
        return dsl.select()
                .from(USER)
                .join(USERSTATUS)
                .on(USERSTATUS.STATUSID.eq(USER.STATUSID))
                .join(MENTOR)
                .on(MENTOR.USERID.eq(USER.MENTORID))
                ;
    }
}
