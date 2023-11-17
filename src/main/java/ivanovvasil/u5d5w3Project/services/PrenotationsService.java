package ivanovvasil.u5d5w3Project.services;

import ivanovvasil.u5d5w3Project.entities.Prenotation;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.repositories.PrenotationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrenotationsService {
  @Autowired
  private PrenotationsRepository prenotationsRepository;

  public Prenotation save(Prenotation prenotation) {
    return prenotationsRepository.save(prenotation);
  }

  public Page<Prenotation> findAll(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return prenotationsRepository.findAll(pageable);
  }

  public Prenotation findById(Long id) throws NotFoundException {
    return prenotationsRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotation not found!"));
  }

  public void findByIdAndDelete(Long id) throws NotFoundException {
    prenotationsRepository.deleteById(id);
  }


}
