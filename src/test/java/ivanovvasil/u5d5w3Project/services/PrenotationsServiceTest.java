package ivanovvasil.u5d5w3Project.services;

import com.github.javafaker.Faker;
import ivanovvasil.u5d5w3Project.entities.Event;
import ivanovvasil.u5d5w3Project.entities.Prenotation;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.enums.Role;
import ivanovvasil.u5d5w3Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.payloadsDTO.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PrenotationsServiceTest {
  Faker faker = new Faker(Locale.ITALY);
  @Autowired
  UserAuthenticationService authenticationService;
  @Autowired
  private UsersService usersService;
  @Autowired
  private EventsService eventsService;
  @Autowired
  private PrenotationsService prenotationsService;

  private User user;
  private Event event;
  private Prenotation prenotation;

  public User getManager() {
    return usersService.findByEmail("admin@gmail.com");
  }

  @BeforeEach
  public void setup() throws IOException {
    UserDTO userDTO = new UserDTO("Franck", "Johnson", "test87@gmail.com", "pic1");
    CreatedUserDTO createdUserDTO = authenticationService.registerUser(userDTO);
    user = new User(createdUserDTO.id(), createdUserDTO.name(),
            createdUserDTO.surname(), createdUserDTO.email(), Role.USER);

    EventResponseDTO savedEvent = eventsService.save(getManager(), new EventDTO("Meeting", "meeting", "2023-11-25", "Rome", 5, "Img1"));
    event = eventsService.findById(savedEvent.event_id());
    Prenotation savedPrenotation = Prenotation.builder().event(event).user(user).build();
    prenotation = prenotationsService.save(savedPrenotation);
  }

  @AfterEach
  public void afterAllTests() throws IOException {
    usersService.findByIdAndDelete(user.getId());
    eventsService.findByIdAndDelete(event.getId());
    prenotationsService.findByIdAndDelete(prenotation.getId());
  }

  @Test
  public void testSavePrenotationNotNull() throws IOException {
    assertNotNull(prenotation);
  }

  @Test
  public void testDeletePrenotationReturnNotFoundAfterFindById() throws IOException {
    prenotationsService.findByIdAndDelete(prenotation.getId());
    Assertions.assertThrows(NotFoundException.class, () -> {
      prenotationsService.findById(prenotation.getId());
    });
  }

  @Test
  public void testBookEventReturnAlreadyBooked() throws IOException {
    Assertions.assertThrows(BadRequestException.class, () -> {
      prenotationsService.bookEvent(user, event.getId());
      PrenotationResponseDTO prenotation = prenotationsService.bookEvent(user, event.getId());
    });
  }

}
