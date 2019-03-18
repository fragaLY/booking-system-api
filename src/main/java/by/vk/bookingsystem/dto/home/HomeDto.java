package by.vk.bookingsystem.dto.home;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonRootName("home")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Getter
public class HomeDto {

  @NotBlank(message = "Home id cannot be blank")
  private final String id;

  @NotBlank(message = "Home name cannot be blank")
  private final String name;
}
