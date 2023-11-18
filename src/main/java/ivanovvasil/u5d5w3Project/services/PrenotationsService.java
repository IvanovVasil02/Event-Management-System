package ivanovvasil.u5d5w3Project.services;

import ivanovvasil.u5d5w3Project.entities.Event;
import ivanovvasil.u5d5w3Project.entities.Prenotation;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.exceptions.NoAvailablePlacesException;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.payloadsDTO.EventResponseDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.PrenotationResponseDTO;
import ivanovvasil.u5d5w3Project.repositories.PrenotationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotationsService {
  @Autowired
  EventsService eventsService;
  @Autowired
  private PrenotationsRepository prenotationsRepository;

  public Prenotation save(Prenotation prenotation) {
    return prenotationsRepository.save(prenotation);
  }

  public Page<Prenotation> findAll(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return prenotationsRepository.findAll(pageable);
  }

  public List<Prenotation> findAllByUser(User user) {
    return prenotationsRepository.findAllByUser(user);
  }

  public Prenotation findById(Long id) throws NotFoundException {
    return prenotationsRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotation not found!"));
  }

  public void findByIdAndDelete(Long id) throws NotFoundException {
    prenotationsRepository.deleteById(id);
  }

  public PrenotationResponseDTO bookEvent(User user, Long id) throws NoAvailablePlacesException {
    Event event = eventsService.findById(id);
    event.setAvailablePlaces(event.getAvailablePlaces() - 1);
    EventResponseDTO eventResponseDTO = eventsService.converToEventDTO(event);
    int totalPrenotation = prenotationsRepository.findAllById(id).size();
    if (event.getAvailablePlaces() > 0) {
      Prenotation prenotation = Prenotation.builder().user(user).event(event).build();
      prenotationsRepository.save(prenotation);
      return new PrenotationResponseDTO(user, eventResponseDTO);
    } else {
      throw new NoAvailablePlacesException();
    }

  }

}
