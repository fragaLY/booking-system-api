package by.vk.bookingsystem.controller;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.ReportService;
import by.vk.bookingsystem.service.impl.report.OrderReportServiceImpl;
import by.vk.bookingsystem.service.impl.report.UserReportServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller to work with reports
 *
 * @author Vadzim_Kavalkou
 */
@CrossOrigin(
    maxAge = 3600,
    origins = "*",
    methods = {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.HEAD})
@RestController
@RequestMapping("/reports")
public class ReportController {

  private static final String USERS_REPORT_FILE_TEMPLATE = "users-report-%s.docx";
  private static final String ORDERS_REPORT_FILE_TEMPLATE = "orders-report-%s.docx";
  private static final String DATE_TIME_FORMATTER_PATTERN = "yyyy-MM-dd_HH-mm-ss";
  private static final String MEDIA_TYPE =
      "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

  private final ReportService userReportService;
  private final ReportService orderReportService;

  /**
   * The constructor of class. Uses autowiring via it.
   *
   * @param userReportService - the service with business logic to work with reports
   */
  @Autowired
  public ReportController(
      final UserReportServiceImpl userReportService,
      final OrderReportServiceImpl orderReportService) {
    this.userReportService = userReportService;
    this.orderReportService = orderReportService;
  }

  /**
   * Returns the options
   *
   * @return {@link ResponseEntity}
   */
  @ApiOperation(value = "Get options", notes = "The options will be returned")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Options were retrieved"),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "Options were not found",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @RequestMapping(value = "**", method = RequestMethod.OPTIONS)
  @ResponseBody
  public ResponseEntity<Void> getOptions() {
    return ResponseEntity.ok()
        .allow(HttpMethod.GET, HttpMethod.OPTIONS, HttpMethod.HEAD)
        .contentType(MediaType.parseMediaType(MEDIA_TYPE))
        .cacheControl(CacheControl.maxAge(3600, TimeUnit.SECONDS))
        .build();
  }

  /**
   * Returns users report between selected dates
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link ResponseEntity}
   */
  @ApiOperation(
      value = "Generates users report",
      notes = "The file will be sent to load",
      response = Resource.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Report had been generated", response = Resource.class),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "Users were not found for current date frame",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @GetMapping(value = "/users")
  @ResponseBody
  public ResponseEntity<Resource> generateUserReport(
      @ApiParam(
              value = "The start date of searching for users. Date format yyyy-MM-dd",
              defaultValue = "0001-01-01")
          @RequestParam(value = "from", defaultValue = "0001-01-01")
          @DateTimeFormat(pattern = "yyyy-MM-dd")
          final LocalDate from,
      @ApiParam(
              value = "The end date of searching for users. Date format yyyy-MM-dd",
              defaultValue = "9999-12-31")
          @RequestParam(value = "to", defaultValue = "9999-12-31")
          @DateTimeFormat(pattern = "yyyy-MM-dd")
          final LocalDate to)
      throws IOException {

    final DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_PATTERN);
    final String formatterDateTime = dateTimeFormatter.format(LocalDateTime.now(Clock.systemUTC()));
    final String fileName = String.format(USERS_REPORT_FILE_TEMPLATE, formatterDateTime);

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(MEDIA_TYPE))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"".concat(fileName).concat("\""))
        .body(userReportService.generateReportResource(from, to));
  }

  /**
   * Returns orders report between selected dates
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link ResponseEntity}
   */
  @ApiOperation(
      value = "Generates orders report",
      notes = "The file will be sent to load",
      response = Resource.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Report had been generated", response = Resource.class),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "Orders were not found for current date frame",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @GetMapping(value = "/orders")
  @ResponseBody
  public ResponseEntity<Resource> generateOrderReport(
      @ApiParam(
              value = "The start date of searching for orders. Date format yyyy-MM-dd",
              defaultValue = "0001-01-01")
          @RequestParam(value = "from", defaultValue = "0001-01-01")
          @DateTimeFormat(pattern = "yyyy-MM-dd")
          final LocalDate from,
      @ApiParam(
              value = "The end date of searching for orders. Date format yyyy-MM-dd",
              defaultValue = "9999-12-31")
          @RequestParam(value = "to", defaultValue = "9999-12-31")
          @DateTimeFormat(pattern = "yyyy-MM-dd")
          final LocalDate to)
      throws IOException {

    final DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_PATTERN);
    final String formatterDateTime = dateTimeFormatter.format(LocalDateTime.now(Clock.systemUTC()));
    final String fileName = String.format(ORDERS_REPORT_FILE_TEMPLATE, formatterDateTime);

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(MEDIA_TYPE))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"".concat(fileName).concat("\""))
        .body(orderReportService.generateReportResource(from, to));
  }
}
