package ee.ituk.api.user.domain;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity(name = "User")
@Table(name = "user", schema = "public")
@SQLDelete(sql = "UPDATE user SET deleted_at = now() WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE user SET deleted_at = NOW() WHERE 1 = 1")
@Where(clause = "deleted_at IS null")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
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
  private Role role = Role.MEMBER;
  private boolean archived = false;
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt = LocalDateTime.now();

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

  public String getFullName() {
    return firstName + " " + lastName;
  }
}
