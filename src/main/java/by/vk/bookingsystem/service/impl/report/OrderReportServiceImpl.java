package by.vk.bookingsystem.service.impl.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.report.OrdersWordDocument;
import by.vk.bookingsystem.report.WordDocument;
import by.vk.bookingsystem.report.setting.ReportSettings;
import by.vk.bookingsystem.report.setting.ReportType;
import by.vk.bookingsystem.report.statistics.CostStatistics;
import by.vk.bookingsystem.service.ReportService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SystemUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
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

  /**
   * The constructor with parameters.
   *
   * @param orderDao {@link OrderDao}
   * @param orderConverter {@link OrderConverter}
   * @param environment {@link Environment}
   */
  @Autowired
  public OrderReportServiceImpl(
      final OrderDao orderDao, final OrderConverter orderConverter, final Environment environment) {
    this.orderDao = orderDao;
    this.orderConverter = orderConverter;
    this.environment = environment;
  }

  /**
   * Generates orders report between selected dates
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   */
  @SneakyThrows(IOException.class)
  @Override
  // todo vk: investigate possibility of caching
  public Resource generateReportResource(final LocalDate from, final LocalDate to) {

    if (from.isAfter(to)) {
      LOGGER.warn(INVALID_REPORT_DATES_LOG);
      throw new IllegalArgumentException(environment.getProperty(INVALID_DATES));
    }

    final List<Order> orders = orderDao.findOrdersRegisteredBetweenDates(from, to);

    if (orders == null || orders.isEmpty()) {
      LOGGER.warn(ORDERS_NOT_FOUND_LOG, from, to);
      throw new ObjectNotFoundException(String.format(ORDERS_NOT_FOUND, from, to));
    }

    final List<OrderDto> ordersDto =
        orders.stream()
            .filter(Objects::nonNull)
            .map(orderConverter::convertToDto)
            .collect(Collectors.toList());

    final LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

    final LongSummaryStatistics durationStatistics =
        ordersDto.stream()
            .mapToLong(
                order ->
                    ChronoUnit.DAYS.between(
                        order.getFrom().atStartOfDay(), order.getTo().atStartOfDay()))
            .summaryStatistics();

    final CostStatistics costStatistics =
        ordersDto.stream().map(OrderDto::getCost).collect(CostStatistics.statistics());

    final IntSummaryStatistics guestsStatistics =
        ordersDto.stream().mapToInt(OrderDto::getGuests).summaryStatistics();

    final WordDocument wordDocument =
        new OrdersWordDocument(new XWPFDocument(), ordersDto)
            .addTableHeader(OrdersWordDocument.ORDERS_HEADERS)
            .addTableRows()
            .addAverageStatistics(durationStatistics, costStatistics, guestsStatistics)
            .addSummaryStatistics(durationStatistics, costStatistics, guestsStatistics)
            .addImage(
                SystemUtils.IS_OS_WINDOWS
                    ? ReportSettings.LOGO_PATH_WINDOWS.getValue()
                    : ReportSettings.LOGO_PATH_LINUX.getValue())
            .addFooter(from, to, ordersDto.size(), ReportType.ORDERS, now);

    byte[] outputByteArray;

    try (final XWPFDocument document = wordDocument.getDocument();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      document.write(byteArrayOutputStream);
      outputByteArray = byteArrayOutputStream.toByteArray();
    }

    return new ByteArrayResource(outputByteArray);
  }
}
