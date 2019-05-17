package by.vk.bookingsystem.service.impl.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.OrderConverter;
import by.vk.bookingsystem.dao.OrderDao;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.report.OrdersWordDocument;
import by.vk.bookingsystem.report.WordDocument;
import by.vk.bookingsystem.report.setting.ReportSettings;
import by.vk.bookingsystem.report.setting.ReportType;
import by.vk.bookingsystem.report.statistics.CostStatistics;
import by.vk.bookingsystem.service.ReportService;
import lombok.SneakyThrows;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 * The service for order reporting
 *
 * @author Vadzim_Kavalkou
 */
@Service("orderReportService")
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class OrderReportServiceImpl implements ReportService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderReportServiceImpl.class);

  private static final String INVALID_DATES = "dates.invalid";
  private static final String ORDERS_NOT_FOUND_LOG = "The orders were not found from {} and {}";
  private static final String ORDERS_NOT_FOUND = "The orders were not found from %s and %s";
  private static final String INVALID_REPORT_DATES_LOG =
      "The from date should be before to date for report";

  private final OrderDao orderDao;
  private final OrderConverter orderConverter;
  private final Environment environment;
  private final Resource imageResource;
  /**
   * The constructor with parameters.
   *
   * @param orderDao {@link OrderDao}
   * @param orderConverter {@link OrderConverter}
   * @param environment {@link Environment}
   * @param resourceLoader {@link ResourceLoader}
   */
  @Autowired
  public OrderReportServiceImpl(
      final OrderDao orderDao,
      final OrderConverter orderConverter,
      final Environment environment,
      final ResourceLoader resourceLoader) {
    this.orderDao = orderDao;
    this.orderConverter = orderConverter;
    this.environment = environment;
    this.imageResource = resourceLoader.getResource(ReportSettings.LOGO_RESOURCE.getValue());
  }

  /**
   * Generates orders report between selected dates
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   */
  @SneakyThrows(IOException.class)
  @Override
  public Resource generateReportResource(final LocalDate from, final LocalDate to) {

    if (from.isAfter(to)) {
      LOGGER.warn(INVALID_REPORT_DATES_LOG);
      throw new IllegalArgumentException(environment.getProperty(INVALID_DATES));
    }

    final List<OrderDto> orders =
        orderDao.findOrdersRegisteredBetweenDates(from, to).stream()
            .filter(Objects::nonNull)
            .map(orderConverter::convertToDto)
            .collect(Collectors.toList());

    if (orders.isEmpty()) {
      LOGGER.warn(ORDERS_NOT_FOUND_LOG, from, to);
      throw new ObjectNotFoundException(String.format(ORDERS_NOT_FOUND, from, to));
    }

    final WordDocument wordDocument;

    try (final InputStream imageInputStream = imageResource.getInputStream()) {
      final LongSummaryStatistics durationStatistics =
          orders.stream()
              .mapToLong(
                  order ->
                      ChronoUnit.DAYS.between(
                          order.getFrom().atStartOfDay(), order.getTo().atStartOfDay()))
              .summaryStatistics();

      final CostStatistics costStatistics =
          orders.stream().map(OrderDto::getCost).collect(CostStatistics.statistics());

      final IntSummaryStatistics guestsStatistics =
          orders.stream().mapToInt(OrderDto::getGuests).summaryStatistics();

      wordDocument =
          new OrdersWordDocument(new XWPFDocument(), orders)
              .addTableHeader(OrdersWordDocument.ORDERS_HEADERS)
              .addTableRows()
              .addAverageStatistics(durationStatistics, costStatistics, guestsStatistics)
              .addSummaryStatistics(durationStatistics, costStatistics, guestsStatistics)
              .addImage(imageInputStream)
              .addFooter(
                  from, to, orders.size(), ReportType.ORDERS, LocalDateTime.now(Clock.systemUTC()));
    }

    byte[] outputByteArray;

    try (final XWPFDocument document = wordDocument.getDocument();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      document.write(byteArrayOutputStream);
      outputByteArray = byteArrayOutputStream.toByteArray();
    }

    return new ByteArrayResource(outputByteArray);
  }
}
