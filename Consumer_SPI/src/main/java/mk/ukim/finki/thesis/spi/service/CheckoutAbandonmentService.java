package mk.ukim.finki.thesis.spi.service;

import mk.ukim.finki.thesis.common.enums.TimePeriodType;
import mk.ukim.finki.thesis.common.helper.TimePeriodHelper.TimePeriod;
import mk.ukim.finki.thesis.spi.service.impl.CheckoutAbandonmentServiceImpl.ReasonAndProducts;

import java.util.List;
import java.util.Map;

/**
 * Service for retrieving the abandoned checkouts.
 */
public interface CheckoutAbandonmentService {

  /**
   * Returns a map of each time period accompanied by a list of {@link ReasonAndProducts} object which holds the
   * products data for each cancellation reason.
   *
   * @param timePeriod the time period.
   * @param type the time period type
   *
   * @return a map of time period and a list of matching reasons and products.
   */
  Map<String, List<ReasonAndProducts>> getProductsForCancellationReason(TimePeriod timePeriod,
                                                                        TimePeriodType type);
}
