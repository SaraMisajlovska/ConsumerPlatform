package mk.ukim.finki.thesis.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
@RequiredArgsConstructor
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

  @JsonIgnore
  @ManyToMany(mappedBy = "products")
  private List<Cart> carts;

  @JsonIgnore
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<CartProduct> cartProducts;
}
