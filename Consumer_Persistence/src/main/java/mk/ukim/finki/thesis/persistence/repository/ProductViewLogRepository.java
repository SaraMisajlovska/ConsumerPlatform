package mk.ukim.finki.thesis.persistence.repository;

import mk.ukim.finki.thesis.persistence.model.ProductViewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductViewLogRepository extends JpaRepository<ProductViewLog, Long> {

  List<ProductViewLog> findAllByTimeOfViewingBetween(LocalDateTime from, LocalDateTime to);
}
