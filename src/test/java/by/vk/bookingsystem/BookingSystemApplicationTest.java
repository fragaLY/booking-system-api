package by.vk.bookingsystem;

import static org.assertj.core.api.Assertions.assertThat;

import by.vk.bookingsystem.controller.HomeController;
import by.vk.bookingsystem.controller.OrderController;
import by.vk.bookingsystem.controller.PriceController;
import by.vk.bookingsystem.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingSystemApplicationTest {

  @Autowired private HomeController homeController;
  @Autowired private OrderController orderController;
  @Autowired private PriceController priceController;
  @Autowired private UserController userController;

  @Test
  public void contextLoads() {
    assertThat(homeController).isNotNull();
    assertThat(orderController).isNotNull();
    assertThat(priceController).isNotNull();
    assertThat(userController).isNotNull();
  }
}
