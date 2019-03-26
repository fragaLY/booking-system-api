package by.vk.bookingsystem;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingSystemApplication.class)
public class BookingSystemApplicationTests {

  // todo vk: fix tests
  public void contextLoads() {
    System.out.println("Loaded");
  }
}
