package mk.ukim.finki.thesis.persistence.model;

import jakarta.persistence.*;
import lombok.Data;
import mk.ukim.finki.thesis.persistence.enums.CartStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cart")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_id")
  private Long cartId;

  @Column(name = "external_cart_id", nullable = false)
  private Long externalCartId;

  @Column(name = "time_of_update", nullable = false)
  private LocalDate timeOfUpdate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private CartStatus status;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

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

  public void removeCartProduct(CartProduct cartProduct) {
    this.cartProducts.removeIf(cp -> cp.getProduct().equals(cartProduct.getProduct()));
  }
}
