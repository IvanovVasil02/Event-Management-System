package ivanovvasil.u5d5w3Project.services;

import com.github.javafaker.Faker;
import ivanovvasil.u5d5w3Project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

@SpringBootTest
public class PrenotationsServiceTest {
  Faker faker = new Faker(Locale.ITALY);
  @Autowired
  private UsersService usersService;
  @Autowired
  private EventsService eventsService;

  public User getManager() {
    return usersService.findByEmail("admin@gmail.com");
  }

//  public Prenotation getPrenoation() {
//    UserDTO user = new UserDTO("Franck", "Johnson", faker.internet().emailAddress(), "pic1");
//    EventResponseDTO event = eventsService.save(getManager(),new EventDTO("Meeting", "meeting", "2023-11-25", "Rome", 7, "Img1"));
//    Event found = eventsService.findById(event.event_id());
//    User found = usersService.findById(user.);
//    Prenotation prenotation = new Prenotation(found, );
//    prenotationsService.save(prenotation);
//  }

}
