package mk.ukim.finki.thesis.common.helper;

import mk.ukim.finki.thesis.common.enums.TimePeriodType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * Helper class for time period related functionalities.
 */
public final class TimePeriodHelper {

  /**
   * Record which signifies a from-to time period.
   *
   * @param fromDate start of the period
   * @param toDate end of the period.
   */
  public record TimePeriod(LocalDateTime fromDate, LocalDateTime toDate) {
  }

  public static List<TimePeriod> getTimePeriods(TimePeriod timePeriod, TimePeriodType type, int n) {

    List<TimePeriod> timePeriods = new LinkedList<>();

    // Add periods before the current time period
    for (int i = n; i > 0; i--) {
      timePeriods.addFirst(createTimePeriod(timePeriod.fromDate, timePeriod.toDate, type, -i));
    }

    // Add current time period
    timePeriods.add(createTimePeriod(timePeriod.fromDate, timePeriod.toDate, type, 0));

    // Add periods after the current time period
    for (int i = 1; i <= n; i++) {
      timePeriods.add(createTimePeriod(timePeriod.fromDate, timePeriod.toDate, type, i));
    }

    return timePeriods;
  }

  private static TimePeriod createTimePeriod(LocalDateTime start, LocalDateTime end, TimePeriodType type, int offset) {
    return switch (type) {
      case date -> new TimePeriod(start.plusDays(offset), end.plusDays(offset));
      case month -> {

        LocalDateTime firstOfMonth = start.plusMonths(offset).withDayOfMonth(1);
        LocalDateTime lastOfMonth = firstOfMonth.withDayOfMonth(firstOfMonth.toLocalDate().lengthOfMonth());
        yield new TimePeriod(firstOfMonth, lastOfMonth);
      }
      case quarter -> {
        LocalDateTime firstOfQuarter = start.plusMonths(offset * 3L).withDayOfMonth(1);
        LocalDateTime lastOfQuarter = firstOfQuarter.plusMonths(2)
                .withDayOfMonth(firstOfQuarter.plusMonths(2).toLocalDate().lengthOfMonth());
        yield new TimePeriod(firstOfQuarter, lastOfQuarter);
      }
      case year -> {
        LocalDateTime firstOfYear = start.plusYears(offset).withMonth(1).withDayOfMonth(1);
        LocalDateTime lastOfYear = firstOfYear.withMonth(12)
                .withDayOfMonth(firstOfYear.withMonth(12).toLocalDate().lengthOfMonth());
        yield new TimePeriod(firstOfYear, lastOfYear);
      }
    };
  }

  /**
   * Formats the time period based on the types format pattern.
   *
   * @param timePeriod the time period
   * @param type the time period type
   *
   * @return a formatted time period in string form.
   */
  public static String formatTimePeriod(TimePeriod timePeriod, TimePeriodType type) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(type.getFormatPattern());
    return timePeriod.fromDate().toLocalDate().format(formatter);
  }
}
