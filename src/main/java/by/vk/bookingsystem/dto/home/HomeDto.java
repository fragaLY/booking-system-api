package by.vk.bookingsystem.dto.home;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The data transfer object of home.
 *
 * @author Vadzim_Kavalkou
 */
@JsonRootName("home")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HomeDto {

  @NotBlank(message = "Home id cannot be blank")
  private String id;

  @NotBlank(message = "Home name cannot be blank")
  private String name;
}
