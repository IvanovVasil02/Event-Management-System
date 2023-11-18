package ivanovvasil.u5d5w3Project.repositories;

import ivanovvasil.u5d5w3Project.entities.Prenotation;
import ivanovvasil.u5d5w3Project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrenotationsRepository extends JpaRepository<Prenotation, Long> {

  List<Prenotation> findAllByUser(User user);
}
