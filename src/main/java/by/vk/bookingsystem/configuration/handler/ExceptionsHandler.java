package by.vk.bookingsystem.configuration.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

import by.vk.bookingsystem.exception.ErrorDetails;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The handler of exception that appears during the application work
 *
 * @author Vadzim_Kavalkou
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

  private static final String FORMAT = "Cause in %s , reason %s %n.";

  /**
   * Handles the {@link IllegalArgumentException}
   *
   * @param exception - the exception
   * @return {@link ResponseEntity}
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(
      final IllegalArgumentException exception) {

    final ErrorDetails errorDetails =
        new ErrorDetails(BAD_REQUEST, BAD_REQUEST.value(), exception.getMessage());

    return ResponseEntity.status(BAD_REQUEST).body(errorDetails);
  }

  /**
   * Handles the {@link ObjectNotFoundException}
   *
   * @param exception - the exception
   * @return {@link ResponseEntity}
   */
  @ExceptionHandler({ObjectNotFoundException.class})
  public ResponseEntity<Object> handleObjectNotFoundException(
      final ObjectNotFoundException exception) {

    final ErrorDetails errorDetails =
        new ErrorDetails(NOT_FOUND, NOT_FOUND.value(), exception.getMessage());

    return ResponseEntity.status(NOT_FOUND).body(errorDetails);
  }

  /**
   * Handles the {@link AccessDeniedException}
   *
   * @param exception - the exception
   * @return {@link ResponseEntity}
   */
  @ExceptionHandler(value = {AccessDeniedException.class})
  public ResponseEntity<Object> handleUnauthorizedException(final AccessDeniedException exception) {

    final ErrorDetails errorDetails =
        new ErrorDetails(FORBIDDEN, FORBIDDEN.value(), exception.getMessage());

    return ResponseEntity.status(FORBIDDEN).body(errorDetails);
  }

  /**
   * Handle the {@link MethodArgumentNotValidException}
   *
   * @param exception - the exception
   * @param headers - the headers
   * @param status - the status
   * @param request - the request
   * @return {@link ResponseEntity}
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException exception,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final String message =
        exception.getBindingResult().getAllErrors().stream()
            .map(error -> String.format(FORMAT, error.getObjectName(), error.getDefaultMessage()))
            .collect(Collectors.joining());

    final ErrorDetails errorDetails = new ErrorDetails(status, status.value(), message);

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  /**
   * Handle the {@link HttpRequestMethodNotSupportedException}
   *
   * @param exception - the exception
   * @param headers - the headers
   * @param status - the status
   * @param request - the request
   * @return {@link ResponseEntity}
   */
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      final HttpRequestMethodNotSupportedException exception,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  /**
   * Handle {@link NoHandlerFoundException}
   *
   * @param exception - the exception
   * @param headers - the headers
   * @param status - the status
   * @param request - the request
   * @return {@link ResponseEntity}
   */
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      final NoHandlerFoundException exception,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  /**
   * Handle {@link Exception}
   *
   * @param exception - the exception
   * @param headers - the headers
   * @param status - the status
   * @param request - the request
   * @return {@link ResponseEntity}
   */
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      final Exception exception,
      final @Nullable Object body,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      MissingServletRequestPartException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(
      HttpMessageNotWritableException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
      HttpMediaTypeNotAcceptableException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(
      ServletRequestBindingException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleConversionNotSupported(
      ConversionNotSupportedException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleMissingPathVariable(
      MissingPathVariableException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(
      BindException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
      AsyncRequestTimeoutException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest webRequest) {
    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), exception.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }
}
