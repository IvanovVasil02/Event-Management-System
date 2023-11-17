package ivanovvasil.u5d5w3Project.services;

import ivanovvasil.u5d5w3Project.entities.Event;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.payloadsDTO.EventDTO;
import ivanovvasil.u5d5w3Project.repositories.EventsRepository;
import ivanovvasil.u5d5w3Project.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventsService {
  @Autowired
  private EventsRepository eventsRepository;
  @Autowired
  private UsersRepository usersRepository;


  //to save event post whit runner
  public Event saveEventRunner(Event body) {
    return eventsRepository.save(body);
  }

  public Event save(EventDTO body) throws IOException {
    User user = usersRepository.findById(body.user_id()).orElseThrow(() -> new NotFoundException(body.user_id()));
    Event newEvent = new Event();
    newEvent.setTitle(body.title());
    newEvent.setDescription(body.description());
    newEvent.setLocation(body.location());
    newEvent.setDate(LocalDate.parse(body.date()));
    newEvent.setManager(user);
    return eventsRepository.save(newEvent);
  }

  public Page<Event> findAll(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return eventsRepository.findAll(pageable);
  }

  public List<Event> findAllById(Long id) {
    return eventsRepository.findAllByUserId(id);
  }

  public Event findById(Long id) throws NotFoundException {
    return eventsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public void findByIdAndDelete(Long id) throws NotFoundException {
    eventsRepository.delete(this.findById(id));
  }

  public Event findByIdAndUpdate(Long id, EventDTO body) throws IOException {
    Event event = this.findById(body.user_id());
    User user = usersRepository.findById(body.user_id()).orElseThrow(() -> new NotFoundException(body.user_id()));
    event.setTitle(body.title());
    event.setDescription(body.description());
    event.setLocation(body.location());
    event.setDate(LocalDate.parse(body.date()));
    event.setAvailablePlaces(body.availablePlaces());
    event.setManager(user);
    return event;
  }

  public List<Event> getUserEventsById(Long userId) {
    return eventsRepository.findAllByUserId(userId);
  }


}
