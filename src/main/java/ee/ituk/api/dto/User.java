package ee.ituk.api.dto;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class User {

    @Id
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String cardnumber;
    private String telegram;
    private String password;
    private String studentcode;
    private Integer statusid;
    private String curriculum;
    private String iban;
    private Boolean admin;
    private Boolean archived;
    private LocalDateTime createdat;
    private LocalDateTime updatedat;
    private UserStatus userstatus;
    private Mentor mentor;

}