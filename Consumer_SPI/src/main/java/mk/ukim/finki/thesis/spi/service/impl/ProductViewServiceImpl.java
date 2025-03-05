package mk.ukim.finki.thesis.spi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.common.enums.TimePeriodType;
import mk.ukim.finki.thesis.common.helper.TimePeriodHelper.TimePeriod;
import mk.ukim.finki.thesis.persistence.model.Product;
import mk.ukim.finki.thesis.persistence.service.ProductViewLogPersistenceService;
import mk.ukim.finki.thesis.spi.service.ProductViewService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static mk.ukim.finki.thesis.common.helper.TimePeriodHelper.formatTimePeriod;
import static mk.ukim.finki.thesis.common.helper.TimePeriodHelper.getTimePeriods;

/**
 * Implementation of {@link ProductViewService}.
 */
@Service
@RequiredArgsConstructor
public class ProductViewServiceImpl implements ProductViewService {

  private final ProductViewLogPersistenceService productViewLogPersistenceService;

  @Override
  public Map<String, List<ViewsPerCategory>> getViewsPerPeriodByCategory(TimePeriod timePeriod,
                                                                       TimePeriodType timePeriodType) {

    if (timePeriod == null || timePeriod.fromDate() == null || timePeriod.toDate() == null) {
      return new LinkedHashMap<>();
    }

    List<TimePeriod> timePeriods = getTimePeriods(timePeriod, timePeriodType, 1);

    return timePeriods.stream()
            .collect(toMap((period) -> formatTimePeriod(period, timePeriodType),
                           this::getProductsPerCategoryForTime,
                           (existing, replacement) -> existing,
                           LinkedHashMap::new));
  }

  private List<ViewsPerCategory> getProductsPerCategoryForTime(TimePeriod timePeriod){

   List<Product> products = productViewLogPersistenceService.getAllProductsInTimePeriod(timePeriod);

    Function<Map.Entry<String, Long>, ViewsPerCategory> toViewsPerCategory =
            entry -> new ViewsPerCategory(entry.getKey(), Math.toIntExact(entry.getValue()));

    return products.stream()
            .collect(groupingBy(Product::getCategory, Collectors.counting()))
            .entrySet()
            .stream()
            .map(toViewsPerCategory)
            .toList();
  }
}