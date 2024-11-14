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

  public Product getOrCreateProduct(Long externalProductId, Product product) {
    return productRepository.getByExternalProductId(externalProductId)
            .orElseGet(() -> productRepository.save(product));
  }

  public Product getProduct(Long externalProductId) {
    return productRepository.getByExternalProductId(externalProductId)
            .orElse(null);
  }

  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }
}
