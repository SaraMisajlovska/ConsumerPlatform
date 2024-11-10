package mk.ukim.finki.thesis.kafkaconsumer.handler;

import ecommerce.AddToCart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.thesis.common.enums.MessageKey;
import mk.ukim.finki.thesis.persistence.repository.AddToCartRepository;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddToCartRecordHandler implements RecordHandler {

  private final AddToCartRepository addToCartRepository;

  @Override
  public boolean handles(MessageKey key) {
    return MessageKey.ADD_TO_CART.equals(key);
  }

  @Override
  public void handle(SpecificRecord record) {

    AddToCart addToCart = (AddToCart) record;

    //map activity to avro object
    //save to db
//    addToCartRepository.save(addToCart)
  }
}
