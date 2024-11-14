package mk.ukim.finki.thesis.kafkaconsumer.mapper;

import ecommerce.AddToCart;
import ecommerce.ProductInfo;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.persistence.enums.CartStatus;
import mk.ukim.finki.thesis.persistence.model.Cart;
import mk.ukim.finki.thesis.persistence.model.Product;
import mk.ukim.finki.thesis.persistence.model.User;
import mk.ukim.finki.thesis.persistence.service.CartPersistenceService;
import mk.ukim.finki.thesis.persistence.service.ProductPersistenceService;
import mk.ukim.finki.thesis.persistence.service.UserPersistenceService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AvroObjectsMapper {

  private final CartPersistenceService cartPersistenceService;
  private final UserPersistenceService userPersistenceService;
  private final ProductPersistenceService productPersistenceService;

  public static LocalDate mapTimestampToLocalDate(Long timestamp) {
    if (timestamp == null) {
      throw new IllegalArgumentException("Timestamp cannot be null");
    }
    return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
  }

  public static LocalDateTime mapTimestampToLocalDateTime(Long timestamp) {
    if (timestamp == null) {
      throw new IllegalArgumentException("Timestamp cannot be null");
    }
    return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
  }

  public Product mapToProduct(ProductInfo productInfo) {

    Product product = productPersistenceService.getProduct(productInfo.getProductId());

    if (product != null) {
      return product;
    }

    product = new Product();
    product.setExternalProductId(productInfo.getProductId());
    product.setName(productInfo.getName());
    product.setCategory(productInfo.getCategory());
    product.setPrice(productInfo.getPrice());
    product.setCartProducts(new ArrayList<>());

    return productPersistenceService.saveProduct(product);
  }

  public Cart mapToCart(AddToCart addToCart) {

    Cart cart = cartPersistenceService.getCart(addToCart.getCartId());

    if (cart != null) {

      cart.setStatus(CartStatus.NEW);
      cart.setTimeOfUpdate(mapTimestampToLocalDate(addToCart.getTimestamp()));

    } else {
      User user = userPersistenceService.getUser(addToCart.getUserId());
      cart = new Cart();

      cart.setUser(user);
      cart.setStatus(CartStatus.NEW);
      cart.setExternalCartId(addToCart.getCartId());
      cart.setTimeOfUpdate(mapTimestampToLocalDate(addToCart.getTimestamp()));
    }
    return cartPersistenceService.saveCart(cart);
  }
}
