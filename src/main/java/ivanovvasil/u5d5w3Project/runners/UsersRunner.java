package ivanovvasil.u5d5w3Project.runners;

import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

//@Component
@Order(1)
public class UsersRunner implements CommandLineRunner {
  @Autowired
  UsersService usersService;

  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 20; i++) {
      User user = User.builder().build();
      usersService.saveRunnerUser(user);
    }
  }
}
