package mk.ukim.finki.thesis.persistence.repository;

import mk.ukim.finki.thesis.persistence.enums.CartStatus;
import mk.ukim.finki.thesis.persistence.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findByExternalCartId(Long externalCartId);

  List<Cart> findCartsByStatusAndTimeOfUpdateBetween(CartStatus status, LocalDateTime from, LocalDateTime to);
}
