package ivanovvasil.u5d5w3Project.repositories;

import ivanovvasil.u5d5w3Project.entities.Prenotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotationsRepository extends JpaRepository<Prenotation, Long> {

}
