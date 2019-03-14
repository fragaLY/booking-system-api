package by.vk.bookingsystem.dto.home;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonRootName("home")
@AllArgsConstructor
@Getter
public class HomeDto {

  private final String id;
  private final String name;
}
