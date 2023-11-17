package ivanovvasil.u5d5w3Project.services;

import ivanovvasil.u5d5w3Project.entities.Event;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {
  @Autowired
  private EventsRepository eventsRepository;
 

  //to save event post whit runner
  public Event saveEventRunner(Event body) {
    return eventsRepository.save(body);
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

  public List<Event> getUserEventsById(Long userId) {
    return eventsRepository.findAllByUserId(userId);
  }

}
