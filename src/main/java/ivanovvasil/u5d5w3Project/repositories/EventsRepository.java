package ivanovvasil.u5d5w3Project.repositories;

import ivanovvasil.u5d5w3Project.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {
  @Query("SELECT e FROM Event e WHERE e.manager.id = :id")
  List<Event> findAllByUserId(Long id);

}
