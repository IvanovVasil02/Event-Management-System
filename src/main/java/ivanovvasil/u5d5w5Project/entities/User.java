package ivanovvasil.u5d5w5Project.entities;

import com.github.javafaker.Faker;
import ivanovvasil.u5d5w5Project.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
private String name;
private String surname;
private String email;
private String password;
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
