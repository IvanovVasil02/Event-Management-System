package ivanovvasil.u5d5w5Project.entities;

import com.github.javafaker.Faker;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "EventsBuilder")
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String description;
  private LocalDate date;
  private String location;
  private int availablePlaces;


  public static class EventsBuilder {
    Faker f = new Faker(Locale.ITALY);

    private LocalDate date = getRandomLocalDate(2025);
    private String title = f.esports().event();
    private String description = f.lorem().paragraph();
    private String location = f.address().fullAddress();
    private int availablePlaces = new Random().nextInt(5, 20);

    private LocalDate getRandomLocalDate(int year) {
      long minDay = LocalDate.now().toEpochDay();
      long maxDay = LocalDate.of(year, 12, 28).toEpochDay();
      long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
      return LocalDate.ofEpochDay(randomDay);
    }
  }
}
