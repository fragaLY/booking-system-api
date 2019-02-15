package by.vk.bookingsystem.dto.home;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HomeSetDto {

  @JsonProperty("homes")
  private final Set<HomeDto> homes;

  // todo vk: add links
}
