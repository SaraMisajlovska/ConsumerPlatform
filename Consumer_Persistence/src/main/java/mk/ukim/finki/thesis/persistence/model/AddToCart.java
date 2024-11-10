package mk.ukim.finki.thesis.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "add_to_cart")
public class AddToCart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private ProductInfo product;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "timestamp", nullable = false)
  private Long timestamp;

  // Getters and Setters
}
