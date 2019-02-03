package ee.ituk.api.dto;

import ee.ituk.tables.pojos.Mentor;
import ee.ituk.tables.pojos.Userstatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

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
    private Userstatus userstatus;
    private Mentor mentor;

}