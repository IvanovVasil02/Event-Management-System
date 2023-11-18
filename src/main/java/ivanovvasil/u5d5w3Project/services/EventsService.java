package ivanovvasil.u5d5w3Project.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventsService {
  @Autowired
  private EventsRepository eventsRepository;
  @Autowired
  private UsersRepository usersRepository;
  @Autowired
  private Cloudinary cloudinary;

  //to save event with runner
  public Event runnerSave(Event body) {
    return eventsRepository.save(body);
  }

  public Event save(User admin, EventDTO body) throws IOException {
    User user = usersRepository.findById(admin.getId()).orElseThrow(() -> new NotFoundException(admin.getId()));
    Event newEvent = new Event();
    newEvent.setTitle(body.title());
    newEvent.setDescription(body.description());
    newEvent.setLocation(body.location());
    newEvent.setDate(LocalDate.parse(body.date()));
    if (body.picture() != null) {
      newEvent.setPicture(body.picture());
    } else {
      newEvent.setPicture("https://spotme.com/wp-content/uploads/2020/07/Hero-1.jpg");
    }
    newEvent.setManager(user);
    return eventsRepository.save(newEvent);
  }

  public Page<Event> findAll(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return eventsRepository.findAll(pageable);
  }

  //findAll for runner
  public List<Event> runnerFindAll() {
    return eventsRepository.findAll();
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

  public Event findByIdAndUpdate(User admin, Long id, EventDTO body) throws IOException {
    Event event = this.findById(admin.getId());
    User user = usersRepository.findById(admin.getId()).orElseThrow(() -> new NotFoundException(admin.getId()));
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

  public Event uploadImg(Long id, MultipartFile file) throws IOException {
    Event found = this.findById(id);
    String urlImg = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    found.setPicture(urlImg);
    eventsRepository.save(found);
    return found;
  }
}
