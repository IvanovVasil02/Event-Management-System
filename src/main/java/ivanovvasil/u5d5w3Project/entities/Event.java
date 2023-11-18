package ivanovvasil.u5d5w3Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "EventsBuilder")
@Table(name = "events")
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String description;
  private LocalDate date;
  private String location;
  @Column(name = "available_places")
  private int availablePlaces;
  private String picture;
  @ManyToOne
  @JoinColumn(name = "manger_id")
  private User manager;
  @JsonIgnore
  @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
  private List<Prenotation> prenotationList;

  public static class EventsBuilder {
    Faker f = new Faker(Locale.ITALY);

    private LocalDate date = getRandomLocalDate(2025);
    private String title = f.esports().event();
    private String description = f.lorem().paragraph();
    private String location = f.address().fullAddress();
    private int availablePlaces = new Random().nextInt(5, 20);
    private String picture = f.internet().image();

    private LocalDate getRandomLocalDate(int year) {
      long minDay = LocalDate.now().toEpochDay();
      long maxDay = LocalDate.of(year, 12, 28).toEpochDay();
      long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
      return LocalDate.ofEpochDay(randomDay);
    }
  }
}
