package ivanovvasil.u5d5w3Project.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ivanovvasil.u5d5w3Project.entities.Event;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.payloadsDTO.EventDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.EventResponseDTO;
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

  public EventResponseDTO save(User admin, EventDTO body) throws IOException {
    User user = usersRepository.findById(admin.getId()).orElseThrow(() -> new NotFoundException(admin.getId()));
    Event newEvent = this.ConvertToEvent(body);
    if (body.picture() == null) {
      newEvent.setPicture("https://spotme.com/wp-content/uploads/2020/07/Hero-1.jpg");
    }
    newEvent.setManager(user);
    eventsRepository.save(newEvent);
    return this.ConvertToResponseEventDTO(newEvent);
  }

  public Page<EventResponseDTO> findAll(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Event> eventPage = eventsRepository.findAll(pageable);
    return eventPage.map(this::ConvertToResponseEventDTO);
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

  public EventResponseDTO getEventByid(Long id) throws NotFoundException {
    Event event = this.findById(id);
    return ConvertToResponseEventDTO(event);
  }

  public Event findByIdAndUpdate(User admin, Long id, EventDTO body) throws NotFoundException, IOException {
    Event event = this.findById(id);
    User user = usersRepository.findById(admin.getId()).orElseThrow(() -> new NotFoundException(admin.getId()));
    event.setTitle(body.title());
    event.setDescription(body.description());
    event.setLocation(body.location());
    event.setDate(LocalDate.parse(body.date()));
    event.setAvailablePlaces(body.availablePlaces());
    event.setManager(user);
    return event;
  }

  public void findByIdAndDelete(Long id) throws NotFoundException {
    eventsRepository.delete(this.findById(id));
  }

  public EventResponseDTO ConvertToResponseEventDTO(Event event) {
    return new EventResponseDTO(event.getId(), event.getTitle(), event.getDescription(),
            event.getDate().toString(), event.getLocation(),
            event.getAvailablePlaces(), event.getPicture(),
            event.getManager().getUsername());
  }

  public Event ConvertToEvent(EventDTO event) {
    return Event.builder().title(event.title())
            .description(event.description())
            .date(LocalDate.parse(event.date()))
            .location(event.location())
            .availablePlaces(event.availablePlaces())
            .picture(event.picture()).build();
  }

}
