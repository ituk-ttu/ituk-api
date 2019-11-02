package ee.ituk.api.user.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "user", schema = "public")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String cardNumber;
  private String password;
  private String studentCode;
  private String idCode;
  @ManyToOne
  @JoinColumn( name = "status_id")
  private UserStatus status;
  private String curriculum;
  private String iban;
  @Enumerated(EnumType.STRING)
  private Role role;
  private boolean archived;
  private LocalDate createdAt;
  private LocalDate updatedAt;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !archived;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !archived;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !archived;
  }

  @Override
  public boolean isEnabled() {
    return !archived;
  }
}
