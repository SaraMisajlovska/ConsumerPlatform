package mk.ukim.finki.thesis.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "checkout_abandoned")
public class CheckoutAbandoned {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "cart_id", nullable = false)
  private Long cartId;

  @OneToMany
  @JoinColumn(name = "checkout_abandoned_id")
  private List<ProductInfo> products;

  @Column(name = "timestamp", nullable = false)
  private Long timestamp;

  // Getters and Setters
}
