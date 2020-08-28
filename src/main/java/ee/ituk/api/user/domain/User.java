package ee.ituk.api.user.domain;

import ee.ituk.api.common.domain.PersonalData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

@Entity(name = "User")
@Table(name = "user", schema = "public")
@SQLDelete(sql = "UPDATE user SET deleted_at = now() WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE user SET deleted_at = NOW() WHERE 1 = 1")
@Where(clause = "deleted_at IS null")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails, PersonalData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String cardNumber;
  private String password;
  private String studentCode;
  private String personalCode;
  @ManyToOne
  @JoinColumn( name = "status_id")
  private UserStatus status;
  private String curriculum;
  private String iban;
  @Enumerated(EnumType.STRING)
  @Default
  private Role role = Role.MEMBER;
  @Default
  private boolean archived = false;
  @Default
  private boolean isMentor = false;
  @Default
  private LocalDateTime createdAt = LocalDateTime.now();
  @Default
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
