package mk.ukim.finki.thesis.spi.service;

import mk.ukim.finki.thesis.common.enums.TimePeriodType;
import mk.ukim.finki.thesis.common.helper.TimePeriodHelper.TimePeriod;

import java.util.List;
import java.util.Map;

/**
 * Service for retrieving product views.
 */
public interface ProductViewService {

  record ViewsPerCategory(String category, Integer views) {
  }

  /**
   * Retrieves the views of products in different categories in a given time period;
   *
   * @param timePeriod a given time period
   * @param timePeriodType the time period's type
   *
   * @return a map of {@link ViewsPerCategory} by time period.
   */
  Map<String, List<ViewsPerCategory>> getViewsPerPeriodByCategory(TimePeriod timePeriod, TimePeriodType timePeriodType);
}
