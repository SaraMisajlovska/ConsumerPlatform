package mk.ukim.finki.thesis.kafkaconsumer.handler;

import ecommerce.AddToCart;
import ecommerce.ProductInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.thesis.common.enums.MessageKey;
import mk.ukim.finki.thesis.kafkaconsumer.mapper.AvroObjectsMapper;
import mk.ukim.finki.thesis.persistence.model.Cart;
import mk.ukim.finki.thesis.persistence.model.CartProduct;
import mk.ukim.finki.thesis.persistence.model.Product;
import mk.ukim.finki.thesis.persistence.service.CartPersistenceService;
import mk.ukim.finki.thesis.persistence.service.CartProductPersistenceService;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddToCartRecordHandler implements RecordHandler {

  private final AvroObjectsMapper avroObjectsMapper;
  private final CartPersistenceService cartPersistenceService;
  private final CartProductPersistenceService cartProductPersistenceService;

  @Override
  public boolean handles(MessageKey key) {
    return MessageKey.ADD_TO_CART.equals(key);
  }

  @Override
  @Transactional
  public void handle(SpecificRecord record) {

    AddToCart addToCart = (AddToCart) record;

    Cart cart = avroObjectsMapper.mapToCart(addToCart);

    ProductInfo productInfo = addToCart.getProduct();
    Product product = avroObjectsMapper.mapToProduct(productInfo);

    CartProduct cartProduct = cartProductPersistenceService.getCartProduct(cart, product);
    cartProduct.setQuantity(addToCart.getQuantity());
    cart.addCartProduct(cartProduct);
    cartPersistenceService.saveCart(cart);
  }
}
