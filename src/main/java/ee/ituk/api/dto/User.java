package ee.ituk.api.dto;

import ee.ituk.tables.pojos.Userstatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends ee.ituk.tables.pojos.User {

    private Userstatus userstatus;

    public User(ee.ituk.tables.pojos.User value) {
        super(value);
    }
}