package ivanovvasil.u5d5w3Project.entities;

import com.github.javafaker.Faker;
import ivanovvasil.u5d5w3Project.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Locale;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "UsersBuilder")
@Getter
@Setter
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String surname;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private Role role;

  public static class UsersBuilder {
    Faker f = new Faker(Locale.ITALY);
    private String name = f.name().firstName();
    private String surname = f.name().lastName();
    private String email = f.internet().emailAddress();
    private String password = f.internet().password();
    private Role role = Role.getRandomRole();

  }
}
