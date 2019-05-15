package by.vk.bookingsystem.dto.user;

import java.util.Set;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

/**
 * The set of data transfer object of users.
 *
 * @author Vadzim_Kavalkou
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"users", "page", "pages"})
public class UserSetDto extends ResourceSupport {

  @JsonProperty("users")
  @Valid
  private final Set<UserDto> users;

  @JsonProperty("page")
  private final int pageNumber;

  @JsonProperty("pages")
  private final int totalPages;
}
