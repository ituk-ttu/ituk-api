package ee.ituk.api.door;

import ee.ituk.api.door.domain.Door;
import ee.ituk.api.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDoorsDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String cardNumber;
    private List<Door> doors;

    public UserDoorsDto(User user, List<Door> doors) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.cardNumber = user.getCardNumber();
        this.doors = doors;
    }

}
