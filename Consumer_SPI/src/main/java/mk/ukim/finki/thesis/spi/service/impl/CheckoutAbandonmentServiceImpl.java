package mk.ukim.finki.thesis.spi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.common.enums.CancellationReason;
import mk.ukim.finki.thesis.persistence.model.Cart;
import mk.ukim.finki.thesis.persistence.model.Product;
import mk.ukim.finki.thesis.persistence.service.CartPersistenceService;
import mk.ukim.finki.thesis.spi.service.CheckoutAbandonmentService;
import mk.ukim.finki.thesis.spi.service.enumeration.TimePeriodType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class CheckoutAbandonmentServiceImpl implements CheckoutAbandonmentService {

  private final CartPersistenceService cartPersistenceService;

  public Map<String, List<ReasonAndProducts>> getProductsForCancellationReason(LocalDateTime start,
                                                                                 LocalDateTime end,
                                                                                 TimePeriodType type) {
    List<TimePeriod> timePeriods = getTimePeriods(start, end, type);

    return timePeriods
            .stream()
            .collect(toMap((timePeriod) -> formatTimePeriod(timePeriod, type),
                           this::getProductsPerReasonForTime,
                           (existing, replacement) -> existing,
                           LinkedHashMap::new));

  }

  private List<TimePeriod> getTimePeriods(LocalDateTime start, LocalDateTime end, TimePeriodType type) {

    TimePeriod timePeriodCurrent = new TimePeriod(start, end);
    TimePeriod timePeriodBefore = null;
    TimePeriod timePeriodAfter = null;

    switch (type) {
      case date -> {
        timePeriodBefore = new TimePeriod(start.minusDays(1), end.minusDays(1));
        timePeriodAfter = new TimePeriod(start.plusDays(1), end.plusDays(1));
      }
      case month -> {
        timePeriodBefore = new TimePeriod(start.minusMonths(1), end.minusMonths(1));
        timePeriodAfter = new TimePeriod(start.plusMonths(1), end.plusMonths(1));
      }
      case quarter -> {
        timePeriodBefore = new TimePeriod(start.minusMonths(1), end.minusMonths(3));
        timePeriodAfter = new TimePeriod(start.plusMonths(1), end.plusMonths(3));
      }
      case year -> {
        timePeriodBefore = new TimePeriod(start.minusYears(1), end.minusYears(1));
        timePeriodAfter = new TimePeriod(start.plusYears(1), end.plusYears(1));
      }
    }


    return new LinkedList<>(List.of(timePeriodBefore, timePeriodCurrent, timePeriodAfter));
  }

  private String formatTimePeriod(TimePeriod timePeriod, TimePeriodType type) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(type.getFormatPattern());
    return timePeriod.fromDate.toLocalDate().format(formatter);
  }

  /**
   * Record which holds product data for each {@link CancellationReason}.
   *
   * @param reason the cancellation reason.
   * @param products the list of products in carts that were abandoned with the provided reason.
   * @param productsCount number of products in the list.
   */
  public record ReasonAndProducts(CancellationReason reason, List<Product> products, Integer productsCount) {}

  private List<ReasonAndProducts> getProductsPerReasonForTime(TimePeriod timePeriod) {

    long topN = 5L;
    List<Cart> abandonedCarts = cartPersistenceService.getAbandonedCartsInTimeRange(timePeriod.fromDate,
                                                                                    timePeriod.toDate);

    Function<Map<Product, Long>, List<Product>> sortByCountAndGetTopProducts =
            productCounts -> productCounts.entrySet().stream()
                    .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                    .limit(topN)
                    .map(Map.Entry::getKey)
                    .toList();

    Collector<Product, ?, Map<Product, Long>> groupByProductCount =
            groupingBy(product -> product, Collectors.counting());

    Collector<Cart, Object, List<Product>> topProductsPerCancellationReason = Collectors.collectingAndThen(
            Collectors.flatMapping(cart -> cart.getProducts().stream(), groupByProductCount),
            sortByCountAndGetTopProducts);

    Function<Map.Entry<CancellationReason, List<Product>>, ReasonAndProducts> toReasonAndProductsRecord =
            entry -> new ReasonAndProducts(entry.getKey(), entry.getValue(), entry.getValue().size());

    return abandonedCarts.stream()
            .collect(groupingBy(Cart::getCancellationReason, topProductsPerCancellationReason))
            .entrySet()
            .stream()
            .map(toReasonAndProductsRecord)
            .toList();
  }

  /**
   * Record which signifies a from-to time period.
   *
   * @param fromDate start of the period
   * @param toDate end of the period.
   */
  public record TimePeriod(LocalDateTime fromDate, LocalDateTime toDate) {
  }

}
