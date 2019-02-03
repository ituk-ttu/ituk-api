package ee.ituk.api.dto;

import lombok.Data;

@Data
public class UserInput {
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
    private Integer mentorid;
    private Boolean admin;
    private Boolean archived;
}
