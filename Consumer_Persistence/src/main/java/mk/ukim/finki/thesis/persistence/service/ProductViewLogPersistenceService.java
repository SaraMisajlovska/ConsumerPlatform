package mk.ukim.finki.thesis.persistence.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.common.helper.TimePeriodHelper.TimePeriod;
import mk.ukim.finki.thesis.persistence.model.Product;
import mk.ukim.finki.thesis.persistence.model.ProductViewLog;
import mk.ukim.finki.thesis.persistence.repository.ProductViewLogRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductViewLogPersistenceService {
  private final ProductViewLogRepository productViewLogRepository;

  public void save(ProductViewLog productViewLog) {
    productViewLogRepository.save(productViewLog);
  }

  /**
   * Gets all the viewed products in the provided time period.
   *
   * @param timePeriod the provided time period for narrowing the search.
   *
   * @return all products in time period
   */
  public List<Product> getAllProductsInTimePeriod(TimePeriod timePeriod) {
    return productViewLogRepository.findAllByTimeOfViewingBetween(timePeriod.fromDate(), timePeriod.toDate())
            .stream()
            .map(ProductViewLog::getProduct)
            .collect(Collectors.toList());
  }
}
