package mk.ukim.finki.thesis.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.thesis.common.enums.CancellationReason;
import mk.ukim.finki.thesis.persistence.enums.CartStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cart")
@RequiredArgsConstructor
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_id")
  private Long cartId;

  @Column(name = "external_cart_id", nullable = false)
  private Long externalCartId;

  @Column(name = "time_of_update", nullable = false)
  private LocalDateTime timeOfUpdate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private CartStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "cancellation_reason")
  private CancellationReason cancellationReason;

  @ManyToMany
  @JoinTable(
          name = "cart_product",
          joinColumns = @JoinColumn(name = "cart_id"),
          inverseJoinColumns = @JoinColumn(name = "product_id")
  )
  private List<Product> products;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
  private List<CartProduct> cartProducts = new ArrayList<>();

  public void addCartProduct(CartProduct cartProduct) {
    this.cartProducts.removeIf(cp -> cp.getProduct().equals(cartProduct.getProduct()));
    this.cartProducts.add(cartProduct);
  }

  public void markAsAbandoned(CartProduct cartProduct) {
    this.cartProducts.stream()
            .filter(product -> product.getProduct().equals(cartProduct.getProduct()))
            .findFirst()
            .ifPresent(CartProduct::markAsAbandoned);
  }
}
