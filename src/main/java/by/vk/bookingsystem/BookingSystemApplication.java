package by.vk.bookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * The Booking System Application
 *
 * @author Vadzim_Kavalkou
 */
@SpringBootApplication
@EnableReactiveMongoRepositories
public class BookingSystemApplication {

  /**
   * The main method.
   *
   * @param args the array of {@link String}
   */
  public static void main(String[] args) {
    SpringApplication.run(BookingSystemApplication.class, args);
  }
}
