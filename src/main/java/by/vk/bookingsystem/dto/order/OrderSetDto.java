package by.vk.bookingsystem.dto.order;

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
 * The set of data transfer object of orders.
 *
 * @author Vadzim_Kavalkou
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"orders", "page", "pages"})
public class OrderSetDto extends ResourceSupport {

  @JsonProperty("orders")
  @Valid
  private final Set<OrderDto> orders;

  @JsonProperty("page")
  private final int pageNumber;

  @JsonProperty("pages")
  private final int totalPages;
}
