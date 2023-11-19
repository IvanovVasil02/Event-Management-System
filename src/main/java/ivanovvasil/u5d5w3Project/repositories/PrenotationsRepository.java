package ivanovvasil.u5d5w3Project.repositories;

import ivanovvasil.u5d5w3Project.entities.Prenotation;
import ivanovvasil.u5d5w3Project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrenotationsRepository extends JpaRepository<Prenotation, Long> {

  List<Prenotation> findAllByUser(User user);

  List<Prenotation> findAllById(Long id);

  @Query("SELECT COUNT(p) > 0 FROM Prenotation p " +
          "WHERE p.user.id = :user_id AND p.event.id = :event_id")
  Boolean checkPrenotationAlreadyExistsById(Long user_id, Long event_id);
}
