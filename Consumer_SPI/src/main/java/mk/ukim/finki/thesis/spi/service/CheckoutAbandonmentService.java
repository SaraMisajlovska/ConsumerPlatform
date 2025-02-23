package mk.ukim.finki.thesis.spi.service;

import mk.ukim.finki.thesis.spi.service.enumeration.TimePeriodType;
import mk.ukim.finki.thesis.spi.service.impl.CheckoutAbandonmentServiceImpl.ReasonAndProducts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CheckoutAbandonmentService {

  /**
   * Returns a map of each time period accompanied by a lst of {@link ReasonAndProducts} object which holds the
   * products data for each cancellation reason.
   *
   * @param start the start of the period.
   * @param end the end of the period.
   * @param type the time period type
   *
   * @return a map of time period and a list of matching reasons and products.
   */
  Map<String, List<ReasonAndProducts>> getProductsForCancellationReason(LocalDateTime start,
                                                                        LocalDateTime end,
                                                                        TimePeriodType type);
}
