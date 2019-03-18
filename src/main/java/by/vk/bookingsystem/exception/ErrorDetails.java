package by.vk.bookingsystem.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDetails {

  private final HttpStatus httpStatus;
  private final int errorCode;
  private final String message;
}
