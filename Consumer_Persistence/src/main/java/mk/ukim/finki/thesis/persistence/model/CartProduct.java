package mk.ukim.finki.thesis.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_product")
public class CartProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(name = "quantity", nullable = false)
  private Integer quantity = 0;

  @Column(name = "abandoned", nullable = false)
  private boolean isAbandoned = false;

  public void markAsAbandoned() {
    this.isAbandoned = true;
  }
}
