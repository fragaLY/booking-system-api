package by.vk.bookingsystem.helper;

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
public class CostSummaryStatistics {

  public static Collector<BigDecimal, ?, CostSummaryStatistics> statistics() {
    return Collector.of(
        CostSummaryStatistics::new, CostSummaryStatistics::accept, CostSummaryStatistics::merge);
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

  public CostSummaryStatistics merge(CostSummaryStatistics statistics) {
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
