package mk.ukim.finki.thesis.persistence.repository;

import mk.ukim.finki.thesis.persistence.model.AddToCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddToCartRepository extends JpaRepository<AddToCart, Long> {
}
