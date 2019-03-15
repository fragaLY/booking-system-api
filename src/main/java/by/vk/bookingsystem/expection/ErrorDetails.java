package by.vk.bookingsystem.expection;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@Builder
public class ErrorDetails {

  @JsonIgnore private String developerMessage;

  @JsonIgnore private String propertyPath;

  private HttpStatus status;
  private String outputMessage;
  private Set<String> errors;
}
