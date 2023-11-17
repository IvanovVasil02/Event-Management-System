package ivanovvasil.u5d5w3Project.runners;

import ivanovvasil.u5d5w3Project.entities.Event;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.services.EventsService;
import ivanovvasil.u5d5w3Project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(3)
public class PrenotationsRunner implements CommandLineRunner {
  @Autowired
  UsersService usersService;
  @Autowired
  EventsService eventsService;

  @Override
  public void run(String... args) throws Exception {
    List<User> userList = usersService.runnerFindAll();
    List<Event> eventList = eventsService.runnerFindAll();
    for (int i = 0; i < 20; i++) {

    }
  }
}
