package ivanovvasil.u5d5w3Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javafaker.Faker;
import ivanovvasil.u5d5w3Project.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "UsersBuilder")
@Getter
@Setter
@JsonIgnoreProperties({"password", "role", "enabled", "authorities", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})
@Table(name = "users")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String surname;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private Role role;
  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<Prenotation> prenotationList;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.name()));
  }

  @Override
  public String getUsername() {
    return this.name + " " + this.surname;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


  public static class UsersBuilder {
    Faker f = new Faker(Locale.ITALY);
    private String name = f.name().firstName();
    private String surname = f.name().lastName();
    private String email = f.internet().emailAddress();
    private String password = f.internet().password();
    private Role role = Role.USER;

  }
}
