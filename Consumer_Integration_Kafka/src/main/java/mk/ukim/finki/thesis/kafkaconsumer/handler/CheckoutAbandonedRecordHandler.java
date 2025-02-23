package mk.ukim.finki.thesis.kafkaconsumer.handler;

import ecommerce.CheckoutAbandoned;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.thesis.common.enums.CancellationReason;
import mk.ukim.finki.thesis.common.enums.MessageKey;
import mk.ukim.finki.thesis.kafkaconsumer.mapper.AvroObjectsMapper;
import mk.ukim.finki.thesis.persistence.model.Cart;
import mk.ukim.finki.thesis.persistence.service.CartPersistenceService;
import mk.ukim.finki.thesis.persistence.service.CartProductPersistenceService;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutAbandonedRecordHandler implements RecordHandler {

  private final CartPersistenceService cartPersistenceService;
  private final CartProductPersistenceService cartProductPersistenceService;
  private final AvroObjectsMapper avroObjectsMapper;

  @Override
  public boolean handles(MessageKey key) {
    return MessageKey.CART_ABANDONED.equals(key);
  }

  @Override
  @Transactional
  public void handle(SpecificRecord record) {
    CheckoutAbandoned checkoutAbandoned = (CheckoutAbandoned) record;
    Cart cart = avroObjectsMapper.mapToCart(checkoutAbandoned);

    cart.setCancellationReason(mapToCancellationReason(checkoutAbandoned));

    checkoutAbandoned.getProducts()
            .stream()
            .map(avroObjectsMapper::mapToProduct)
            .map(product -> cartProductPersistenceService.getCartProduct(cart, product))
            .forEach(cart::markAsAbandoned);

    cartPersistenceService.saveCart(cart);
  }

  private static CancellationReason mapToCancellationReason(CheckoutAbandoned checkoutAbandoned) {
    return CancellationReason.valueOf(checkoutAbandoned.getAbandonmentReason().name());
  }
}
