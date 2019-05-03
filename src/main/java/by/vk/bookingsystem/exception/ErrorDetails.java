package by.vk.bookingsystem.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * The container for error details.
 *
 * @author Vadzim_Kavalkou
 */
@Data
@JsonRootName("error")
@JsonPropertyOrder({"httpStatus", "errorCode", "message"})
public class ErrorDetails {

  @JsonProperty("http-status")
  private final HttpStatus httpStatus;

  @JsonProperty("error-code")
  private final int errorCode;

  @JsonProperty("message")
  private final String message;
}
