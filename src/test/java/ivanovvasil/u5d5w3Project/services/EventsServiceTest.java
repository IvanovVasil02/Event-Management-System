package ivanovvasil.u5d5w3Project.services;

import ivanovvasil.u5d5w3Project.entities.Event;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.payloadsDTO.EventDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.EventResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class EventsServiceTest {
  @Autowired
  private UsersService usersService;
  @Autowired
  private EventsService eventsService;
  private Event event;

  public User getManager() {
    return usersService.findByEmail("admin@gmail.com");
  }

  @BeforeEach
  public void setup() throws IOException {
    EventResponseDTO savedEvent = eventsService.save(getManager(), new EventDTO("Meeting", "meeting", "2023-11-25", "Rome", 5, "Img1"));
    event = eventsService.findById(savedEvent.event_id());
  }

  @Test
  public void TestSaveEventNotNull() throws IOException {
    Assertions.assertNotNull(event);
  }


  @Test
  public void TestFindEventByIdThrowsNotFoundException() throws IOException {
    Assertions.assertThrows(NotFoundException.class, () -> {
      Event found = eventsService.findById(4000L);
    });
  }

  @Test
  public void TestFindEventByIdReturnCorrectEvent() throws IOException {
    Event found = eventsService.findById(event.getId());
    System.out.println(event);
    System.out.println(found);
    Assertions.assertEquals(event, found);
  }

  @Test
  public void TestDeleteEventReturnNotFoundAfterFindById() throws IOException {
    eventsService.findByIdAndDelete(event.getId());

    Assertions.assertThrows(NotFoundException.class, () -> {
      Event found = eventsService.findById(event.getId());
    });
  }
  
  @Test
  public void testFindByIdAndUpdateEventReturnUpdatedEvent() throws IOException {
    EventDTO eventUpdates = new EventDTO("Meeting2", "meeting2", "2023-11-25", "Rome2", 7, "Img2");
    Event updatedEvent = eventsService.findByIdAndUpdate(getManager(), event.getId(), eventUpdates);
    Assertions.assertNotEquals(event, updatedEvent);
  }

  @Test
  public void testFindByIdAndUpdateEventReturnNotFound() throws IOException {
    EventDTO eventUpdates = new EventDTO("Meeting2", "meeting2", "2023-11-25", "Rome2", 7, "Img2");
    Assertions.assertThrows(NotFoundException.class, () -> {
      Event updatedEmployee = eventsService.findByIdAndUpdate(getManager(), 3000L, eventUpdates);
    });
  }

}
