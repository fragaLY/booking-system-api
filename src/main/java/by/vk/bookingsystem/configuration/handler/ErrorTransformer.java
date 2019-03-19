package by.vk.bookingsystem.configuration.handler;

import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

@Component
public class ErrorTransformer implements Function<ObjectError, String> {

  private static final String FORMAT = "Cause in %s , reason %s %n.";

  @Override
  public String apply(final ObjectError error) {
    return String.format(FORMAT, error.getObjectName(), error.getDefaultMessage());
  }
}
