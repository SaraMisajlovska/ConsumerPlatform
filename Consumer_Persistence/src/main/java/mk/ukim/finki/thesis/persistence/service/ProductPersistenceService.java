package mk.ukim.finki.thesis.persistence.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.persistence.model.Product;
import mk.ukim.finki.thesis.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductPersistenceService {

  private final ProductRepository productRepository;

  /**
   * Retrieves the product if it exists, or creates a new one based on the product passed as a parameter.
   *
   * @param product the product to be saved if it does not exist.
   *
   * @return a product;
   */
  public Product getOrCreateProduct(Product product) {
    return productRepository.getByExternalProductId(product.getExternalProductId())
            .orElseGet(() -> productRepository.save(product));
  }
}
