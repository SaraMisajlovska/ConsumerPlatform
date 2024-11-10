package mk.ukim.finki.thesis.persistence.repository;

import mk.ukim.finki.thesis.persistence.model.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductViewRepository extends JpaRepository<ProductView, Long> {

}
