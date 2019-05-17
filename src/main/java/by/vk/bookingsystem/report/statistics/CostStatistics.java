package by.vk.bookingsystem.report.statistics;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import java.util.stream.Collector;

import lombok.Getter;
import lombok.ToString;

/**
 * The Cost Summary Statistic to work with reports
 *
 * @author Vadzim_Kavalkou
 */
@Getter
@ToString
public class CostStatistics {

  public static Collector<BigDecimal, ?, CostStatistics> statistics() {
    return Collector.of(CostStatistics::new, CostStatistics::accept, CostStatistics::merge);
  }

  private BigDecimal sum = BigDecimal.ZERO;
  private BigDecimal min;
  private BigDecimal max;

  private long count;

  public void accept(BigDecimal value) {
    if (count == 0) {
      Objects.requireNonNull(value);
      count = 1;
      sum = value;
      min = value;
      max = value;
    } else {
      sum = sum.add(value);
      if (min.compareTo(value) > 0) min = value;
      if (max.compareTo(value) < 0) max = value;
      count++;
    }
  }

  public CostStatistics merge(CostStatistics statistics) {
    if (statistics.count > 0) {
      if (count == 0) {
        count = statistics.count;
        sum = statistics.sum;
        min = statistics.min;
        max = statistics.max;
      } else {
        sum = sum.add(statistics.sum);
        if (min.compareTo(statistics.min) > 0) min = statistics.min;
        if (max.compareTo(statistics.max) < 0) max = statistics.max;
        count += statistics.count;
      }
    }
    return this;
  }

  public BigDecimal getAverage(final MathContext mathContext) {
    return count < 2 ? sum : sum.divide(BigDecimal.valueOf(count), mathContext);
  }
}
