package by.vk.bookingsystem.dto.home;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

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
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id", "name"})
public class HomeDto extends ResourceSupport {

  @NotBlank(message = "Home id cannot be blank")
  @JsonProperty("id")
  private String homeId;

  @NotBlank(message = "Home name cannot be blank")
  private String name;
}
