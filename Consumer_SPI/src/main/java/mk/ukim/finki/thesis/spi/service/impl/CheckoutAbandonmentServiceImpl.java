package mk.ukim.finki.thesis.spi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.common.enums.CancellationReason;
import mk.ukim.finki.thesis.persistence.model.Cart;
import mk.ukim.finki.thesis.persistence.model.Product;
import mk.ukim.finki.thesis.persistence.service.CartPersistenceService;
import mk.ukim.finki.thesis.spi.service.CheckoutAbandonmentService;
import mk.ukim.finki.thesis.common.enums.TimePeriodType;
import mk.ukim.finki.thesis.common.helper.TimePeriodHelper.TimePeriod;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static mk.ukim.finki.thesis.common.helper.TimePeriodHelper.formatTimePeriod;
import static mk.ukim.finki.thesis.common.helper.TimePeriodHelper.getTimePeriods;

/**
 * Implementation of {@link CheckoutAbandonmentService}.
 */
@Service
@RequiredArgsConstructor
public class CheckoutAbandonmentServiceImpl implements CheckoutAbandonmentService {

  private final CartPersistenceService cartPersistenceService;

  public Map<String, List<ReasonAndProducts>> getProductsForCancellationReason(TimePeriod timePeriod,
                                                                                 TimePeriodType type) {

    if (timePeriod == null || timePeriod.fromDate() == null || timePeriod.toDate() == null) {
      return new LinkedHashMap<>();
    }

    List<TimePeriod> timePeriods = getTimePeriods(timePeriod, type, 1);

    return timePeriods
            .stream()
            .collect(toMap((period) -> formatTimePeriod(period, type),
                           this::getProductsPerReasonForTime,
                           (existing, replacement) -> existing,
                           LinkedHashMap::new));

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
    List<Cart> abandonedCarts = cartPersistenceService.getAbandonedCartsInTimeRange(timePeriod);

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
}
