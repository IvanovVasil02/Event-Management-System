package ivanovvasil.u5d5w5Project.runners;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
public class testRunner implements CommandLineRunner {
  @Override
  public void run(String... args) throws Exception {
    Faker f = new Faker(Locale.ITALY);
    System.out.println(f.date().future(1, TimeUnit.DAYS));
  }
}
