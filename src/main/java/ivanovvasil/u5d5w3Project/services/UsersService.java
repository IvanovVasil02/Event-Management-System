package ivanovvasil.u5d5w3Project.services;

import ivanovvasil.u5d5w3Project.entities.Event;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserDTO;
import ivanovvasil.u5d5w3Project.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UsersService {
  @Autowired
  private UsersRepository usersRepository;
  @Autowired
  private EventsService eventsService;

  //Save for user runner
  public User saveRunnerUser(User employee) {
    return usersRepository.save(employee);
  }


  //findALl for users runner
  public List<User> runnerFindAll() {
    return usersRepository.findAll();
  }

  public Page<User> findAll(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return usersRepository.findAll(pageable);
  }

  public User findById(Long id) throws NotFoundException {
    return usersRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public User findByEmail(String email) {
    return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("No user found with this email: " + email));
  }

  public User findByIdAndUpdate(Long id, UserDTO body) throws IOException {
    User found = this.findById(id);
    found.setName(body.name());
    found.setSurname(body.surname());
    found.setEmail(body.email());
    return usersRepository.save(found);
  }

  public void findByIdAndDelete(Long id) throws NotFoundException {
    List<Event> eventsList = eventsService.getUserEventsById(id);
    eventsList.forEach(event -> {
      eventsService.findByIdAndDelete(event.getId());
    });
    usersRepository.delete(this.findById(id));
  }


}
