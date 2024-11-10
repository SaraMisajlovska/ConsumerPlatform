package mk.ukim.finki.thesis.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "product_view")
public class ProductView {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private ProductInfo product;

  @Column(name = "timestamp", nullable = false)
  private Instant timestamp;  // Use Instant to handle the timestamp-millis

  // Getters and Setters
}
