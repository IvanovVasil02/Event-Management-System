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
import java.util.Random;

@Component
@Order(2)
public class EventsRunner implements CommandLineRunner {
  @Autowired
  UsersService usersService;
  @Autowired
  EventsService eventsService;

  @Override
  public void run(String... args) throws Exception {

    List<User> userList = usersService.runnerFindAll();
    for (int i = 0; i < 20; i++) {
      Event event = Event.builder().manager(userList.get(new Random().nextInt(0, userList.size()))).build();
      eventsService.runnerSave(event);
    }

  }
}

