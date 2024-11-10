package mk.ukim.finki.thesis.kafkaconsumer.handler;

import ecommerce.CheckoutAbandoned;
import ecommerce.ProductView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.thesis.common.enums.MessageKey;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutAbandonedRecordHandler implements RecordHandler {

  @Override
  public boolean handles(MessageKey key) {
    return MessageKey.CART_ABANDONED.equals(key);
  }

  @Override
  public void handle(SpecificRecord record) {
    ProductView productView = (ProductView) record;

    //map it to a model

    //persist it into db
  }
}
