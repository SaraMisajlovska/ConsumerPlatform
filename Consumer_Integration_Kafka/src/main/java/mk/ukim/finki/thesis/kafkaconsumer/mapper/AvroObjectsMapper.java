package mk.ukim.finki.thesis.kafkaconsumer.mapper;

import ecommerce.AddToCart;
import ecommerce.CheckoutAbandoned;
import ecommerce.ProductInfo;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.persistence.enums.CartStatus;
import mk.ukim.finki.thesis.persistence.model.Cart;
import mk.ukim.finki.thesis.persistence.model.Product;
import mk.ukim.finki.thesis.persistence.service.CartPersistenceService;
import mk.ukim.finki.thesis.persistence.service.ProductPersistenceService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AvroObjectsMapper {

  private final CartPersistenceService cartPersistenceService;
  private final ProductPersistenceService productPersistenceService;

  public static LocalDateTime mapTimestampToLocalDateTime(Long timestamp) {
    if (timestamp == null) {
      throw new IllegalArgumentException("Timestamp cannot be null");
    }
    return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
  }

  public Product mapToProduct(ProductInfo productInfo) {

    Product product = new Product();

    product.setExternalProductId(productInfo.getProductId());
    product.setName(productInfo.getName());
    product.setCategory(productInfo.getCategory());
    product.setPrice(productInfo.getPrice());
    product.setCartProducts(new ArrayList<>());

    return productPersistenceService.getOrCreateProduct(product);
  }

  public Cart mapToCart(AddToCart addToCart) {

    Cart cart = cartPersistenceService.getCart(addToCart.getCartId());

    cart.setStatus(CartStatus.NEW);
    cart.setExternalCartId(addToCart.getCartId());
    cart.setTimeOfUpdate(mapTimestampToLocalDateTime(addToCart.getTimestamp()));

    return cartPersistenceService.saveCart(cart);
  }

  public Cart mapToCart(CheckoutAbandoned checkoutAbandoned) {

    Cart cart = cartPersistenceService.getCart(checkoutAbandoned.getCartId());

    cart.setStatus(CartStatus.ABANDONED);
    cart.setExternalCartId(checkoutAbandoned.getCartId());
    cart.setTimeOfUpdate(mapTimestampToLocalDateTime(checkoutAbandoned.getTimestamp()));

    return cartPersistenceService.saveCart(cart);
  }
}
