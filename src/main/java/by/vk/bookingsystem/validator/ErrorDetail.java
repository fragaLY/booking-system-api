package by.vk.bookingsystem.validator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ErrorDetail {

  private final HttpStatus status;
  private final Exception exception;
}
