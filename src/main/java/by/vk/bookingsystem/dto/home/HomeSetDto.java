package by.vk.bookingsystem.dto.home;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HomeSetDto {

  @JsonProperty("homes")
  private final Set<HomeDto> homes;
}
