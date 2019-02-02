package ee.ituk.api.repository;

import ee.ituk.tables.Userstatus;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static ee.ituk.tables.Userstatus.USERSTATUS;

@Component
public class StatusRepository {

    @Autowired
    private DSLContext dsl;

    public ee.ituk.tables.pojos.Userstatus getUserStatus(int id) {
        return dsl.selectFrom(USERSTATUS)
                .where(USERSTATUS.STATUSID.eq(id))
                .fetchAnyInto(ee.ituk.tables.pojos.Userstatus.class);
    }
}
