package ivanovvasil.u5d5w3Project.services;

import ivanovvasil.u5d5w3Project.entities.Event;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.payloadsDTO.EventDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.EventResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EventsServiceTest {
  EventDTO event = new EventDTO("Meeting", "meeting", "2023-11-25", "Rome", 7, "Img1");
  @Autowired
  private UsersService usersService;
  @Autowired
  private EventsService eventsService;

  public User getManager() {
    return usersService.findByEmail("admin@gmail.com");
  }

  @Test
  public void TestSaveEventNotNull() throws IOException {
    User manager = getManager();
    EventResponseDTO savedEvent = eventsService.save(manager, event);
    assertNotNull(savedEvent);
  }


  @Test
  public void TestFindEventByIdReturnCorrectEvent() throws IOException {
    User manager = getManager();
    EventResponseDTO savedEvent = eventsService.save(manager, event);
    Event found = eventsService.findById(savedEvent.event_id());

    Assertions.assertEquals(savedEvent, eventsService.ConvertToResponseEventDTO(found));
  }

  @Test
  public void TestFindEventByIdThrowsNotFoundException() throws IOException {
    User manager = getManager();
    EventResponseDTO savedEvent = eventsService.save(manager, event);
    Assertions.assertThrows(NotFoundException.class, () -> {
      Event found = eventsService.findById(400L);
    });
  }

  @Test
  public void TestDeleteEventReturnNotFoundAfterFindById() throws IOException {
    EventResponseDTO savedEvent = eventsService.save(getManager(), event);
    eventsService.findByIdAndDelete(savedEvent.event_id());
    Assertions.assertThrows(NotFoundException.class, () -> {
      Event found = eventsService.findById(savedEvent.event_id());
    });
  }

  @Test
  public void testFindByIdAndUpdateEventReturnUpdatedEvent() throws IOException {
    EventResponseDTO savedEvent = eventsService.save(getManager(), event);
    EventDTO eventUpdates = new EventDTO("Meeting2", "meeting2", "2023-11-25", "Rome2", 7, "Img2");
    Event updatedEvent = eventsService.findByIdAndUpdate(getManager(), savedEvent.event_id(), eventUpdates);

    Assertions.assertNotEquals(savedEvent, eventsService.ConvertToResponseEventDTO(updatedEvent));
  }

  @Test
  public void testFindByIdAndUpdateEventReturnNotFound() throws IOException {
    EventResponseDTO savedEvent = eventsService.save(getManager(), event);
    EventDTO eventUpdates = new EventDTO("Meeting2", "meeting2", "2023-11-25", "Rome2", 7, "Img2");
    Assertions.assertThrows(NotFoundException.class, () -> {
      Event updatedEmployee = eventsService.findByIdAndUpdate(getManager(), 3000L, eventUpdates);
    });
  }

}
