package mk.ukim.finki.thesis.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @Column(name = "external_product_id", nullable = false)
  private Long externalProductId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "category", nullable = false)
  private String category;

  @Column(name = "price", nullable = false)
  private Float price;

  @ManyToMany(mappedBy = "products")
  private List<Cart> carts;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<CartProduct> cartProducts;
}
