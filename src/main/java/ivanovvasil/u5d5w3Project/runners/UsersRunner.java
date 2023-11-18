package ivanovvasil.u5d5w3Project.runners;

import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.enums.Role;
import ivanovvasil.u5d5w3Project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class UsersRunner implements CommandLineRunner {
  @Autowired
  UsersService usersService;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    User admin = User.builder()
            .name("admin")
            .email("admin@gmail.com")
            .password(passwordEncoder.encode("1234"))
            .role(Role.MANAGER)
            .build();
    usersService.save(admin);

    for (int i = 0; i < 20; i++) {
      User user = User.builder().build();
      usersService.save(user);
    }
  }
}
