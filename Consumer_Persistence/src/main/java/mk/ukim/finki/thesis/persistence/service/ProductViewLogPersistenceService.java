package mk.ukim.finki.thesis.persistence.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.persistence.model.ProductViewLog;
import mk.ukim.finki.thesis.persistence.repository.ProductViewLogRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductViewLogPersistenceService {
  private final ProductViewLogRepository productViewLogRepository;

  public void save(ProductViewLog productViewLog) {
    productViewLogRepository.save(productViewLog);
  }

}
