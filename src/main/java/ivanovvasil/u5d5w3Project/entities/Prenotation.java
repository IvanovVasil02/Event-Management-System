package ivanovvasil.u5d5w3Project.entities;

import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.events.Event;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "prenotations")
public class Prenotation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @JoinColumn(name = "event_id")
  private Event event;
}
