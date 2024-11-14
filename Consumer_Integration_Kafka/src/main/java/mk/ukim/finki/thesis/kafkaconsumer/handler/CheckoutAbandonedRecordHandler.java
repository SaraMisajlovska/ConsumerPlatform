package mk.ukim.finki.thesis.kafkaconsumer.handler;

import ecommerce.CheckoutAbandoned;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.thesis.common.enums.MessageKey;
import mk.ukim.finki.thesis.persistence.enums.CartStatus;
import mk.ukim.finki.thesis.persistence.model.Cart;
import mk.ukim.finki.thesis.persistence.model.CartProduct;
import mk.ukim.finki.thesis.persistence.service.CartPersistenceService;
import mk.ukim.finki.thesis.persistence.service.CartProductPersistenceService;
import mk.ukim.finki.thesis.persistence.service.ProductPersistenceService;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

import static mk.ukim.finki.thesis.kafkaconsumer.mapper.AvroObjectsMapper.mapTimestampToLocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutAbandonedRecordHandler implements RecordHandler {

  private final CartPersistenceService cartPersistenceService;
  private final ProductPersistenceService productPersistenceService;
  private final CartProductPersistenceService cartProductPersistenceService;

  @Override
  public boolean handles(MessageKey key) {
    return MessageKey.CART_ABANDONED.equals(key);
  }

  @Override
  @Transactional
  public void handle(SpecificRecord record) {
    CheckoutAbandoned checkoutAbandoned = (CheckoutAbandoned) record;
    //logic is weird af - I need to rework it because it does not make sense

    Cart cart = cartPersistenceService.getCart(checkoutAbandoned.getCartId());

    cart.setStatus(CartStatus.ABANDONED);
    cart.setTimeOfUpdate(mapTimestampToLocalDate(checkoutAbandoned.getTimestamp()));

    checkoutAbandoned.getProducts()
            .stream()
            .map(productInfo -> productPersistenceService.getProduct(productInfo.getProductId()))
            .forEach(product -> {
              CartProduct cartProduct = cartProductPersistenceService.getCartProduct(cart, product);
              cart.removeCartProduct(cartProduct);
            });

    cartPersistenceService.saveCart(cart);
  }

}
