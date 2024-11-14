package mk.ukim.finki.thesis.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product_view_log", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "product_id", "time_of_viewing"})})
public class ProductViewLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(name = "time_of_viewing", nullable = false)
  private LocalDateTime timeOfViewing;
}
