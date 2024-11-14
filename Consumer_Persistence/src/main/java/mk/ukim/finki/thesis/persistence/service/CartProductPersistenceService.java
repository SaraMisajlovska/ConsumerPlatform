package mk.ukim.finki.thesis.persistence.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.thesis.persistence.model.Cart;
import mk.ukim.finki.thesis.persistence.model.CartProduct;
import mk.ukim.finki.thesis.persistence.model.Product;
import mk.ukim.finki.thesis.persistence.repository.CartProductRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CartProductPersistenceService {

  private final CartProductRepository cartProductRepository;

  public CartProduct getCartProduct(Cart cart, Product product) {
    return cartProductRepository.findByCartAndProduct(cart, product)
            .orElseGet(() -> newCartProduct(cart, product));
  }

  public CartProduct newCartProduct(Cart cart, Product product) {
    CartProduct cartProduct = new CartProduct();

    cartProduct.setCart(cart);
    cartProduct.setProduct(product);

    return cartProductRepository.save(cartProduct);
  }

}
