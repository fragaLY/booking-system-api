package by.vk.bookingsystem.report;

import java.math.MathContext;
import java.time.temporal.ChronoUnit;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;

import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.helper.CostSummaryStatistics;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * Orders word document
 *
 * @author Vadzim_Kavalkou
 */
@Getter
public final class OrdersWordDocument extends WordDocument {

  public static final Map<Integer, String> ORDERS_HEADERS =
      ImmutableMap.<Integer, String>builder()
          .put(0, "#")
          .put(1, "Owner")
          .put(2, "Homes")
          .put(3, "From")
          .put(4, "To")
          .put(5, "Duration")
          .put(6, "Cost")
          .put(7, "Guests")
          .build();

  private final List<OrderDto> orders;

  /**
   * The constructor of class
   *
   * @param document {@link XWPFDocument}
   * @param orders {@link List} of {@link OrderDto}
   */
  public OrdersWordDocument(final XWPFDocument document, final List<OrderDto> orders) {
    super(document, orders.size() + 3, ORDERS_HEADERS.size());
    this.orders = orders;
  }

  /**
   * Enriches the document with orders information
   *
   * @return {@link OrdersWordDocument}
   */
  //todo vk: refactor this peace of shit
  public OrdersWordDocument addTableRows() {

    orders.forEach(
        order -> {
          final XWPFTableRow row = table.getRow(rowIndex);
          row.getCell(0).setText(String.valueOf(rowIndex++));
          row.getCell(1)
              .setText(
                  order
                      .getOwner()
                      .getLastName()
                      .concat(" ")
                      .concat(order.getOwner().getFirstName()));
          row.getCell(2).setText(String.valueOf(order.getHomes().size()));
          row.getCell(3).setText(order.getFrom().toString());
          row.getCell(4).setText(order.getTo().toString());
          row.getCell(5)
              .setText(
                  String.valueOf(
                      ChronoUnit.DAYS.between(
                          order.getFrom().atStartOfDay(), order.getTo().atStartOfDay())));
          row.getCell(6).setText(order.getCost().toString());
          row.getCell(7).setText(String.valueOf(order.getGuests()));
        });

    final LongSummaryStatistics durationStatistics =
        orders.stream()
            .mapToLong(
                order ->
                    ChronoUnit.DAYS.between(
                        order.getFrom().atStartOfDay(), order.getTo().atStartOfDay()))
            .summaryStatistics();

    final CostSummaryStatistics costStatistics =
        orders.stream().map(OrderDto::getCost).collect(CostSummaryStatistics.statistics());

    final IntSummaryStatistics guestsSummaryStatistics =
        orders.stream().mapToInt(OrderDto::getGuests).summaryStatistics();

    final XWPFTableRow averageRow = table.getRow(rowIndex++);

    averageRow.getCell(0).setText("Average");
    averageRow.getCell(5).setText(String.valueOf(durationStatistics.getAverage()));
    averageRow.getCell(6).setText(costStatistics.getAverage(MathContext.DECIMAL32).toString());
    averageRow.getCell(7).setText(String.valueOf(guestsSummaryStatistics.getAverage()));

    final XWPFTableRow summaryRow = table.getRow(rowIndex);

    summaryRow.getCell(0).setText("Summary");
    averageRow.getCell(5).setText(String.valueOf(durationStatistics.getSum()));
    summaryRow.getCell(6).setText(costStatistics.getSum().toString());
    summaryRow.getCell(7).setText(String.valueOf(guestsSummaryStatistics.getSum()));

    return this;
  }
}
