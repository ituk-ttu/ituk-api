package ee.ituk.api.user.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "recovery_password", schema = "public")
public class PasswordRecovery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
}
