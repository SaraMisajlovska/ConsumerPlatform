package mk.ukim.finki.thesis.persistence.repository;

import mk.ukim.finki.thesis.persistence.model.CheckoutAbandoned;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutAbandonedRepository extends JpaRepository<CheckoutAbandoned, Long> {

}
