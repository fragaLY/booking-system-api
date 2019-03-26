package by.vk.bookingsystem.dto.home;

import java.util.Set;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The set of data transfer object of homes.
 *
 * @author Vadzim_Kavalkou
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class HomeSetDto {

  @JsonProperty("homes")
  @Valid
  private final Set<HomeDto> homes;
}
