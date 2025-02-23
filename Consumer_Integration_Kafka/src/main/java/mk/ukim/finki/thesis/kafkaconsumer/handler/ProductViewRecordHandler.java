package mk.ukim.finki.thesis.kafkaconsumer.handler;

import ecommerce.ProductInfo;
import ecommerce.ProductView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.thesis.common.enums.MessageKey;
import mk.ukim.finki.thesis.kafkaconsumer.mapper.AvroObjectsMapper;
import mk.ukim.finki.thesis.persistence.model.Product;
import mk.ukim.finki.thesis.persistence.model.ProductViewLog;
import mk.ukim.finki.thesis.persistence.service.ProductPersistenceService;
import mk.ukim.finki.thesis.persistence.service.ProductViewLogPersistenceService;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

import static mk.ukim.finki.thesis.kafkaconsumer.mapper.AvroObjectsMapper.mapTimestampToLocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductViewRecordHandler implements RecordHandler {

  private final AvroObjectsMapper avroObjectsMapper;
  private final ProductPersistenceService productPersistenceService;
  private final ProductViewLogPersistenceService productViewLogPersistenceService;

  @Override
  public boolean handles(MessageKey key) {
    return MessageKey.PRODUCT_VIEW.equals(key);
  }

  @Override
  public void handle(SpecificRecord record) {
    ProductView productView = (ProductView) record;

    ProductViewLog productViewLog = mapToProductViewLog(productView);

    productViewLogPersistenceService.save(productViewLog);
  }

  private ProductViewLog mapToProductViewLog(ProductView productView) {

    ProductInfo productInfo = productView.getProduct();
    Product mappedProduct = avroObjectsMapper.mapToProduct(productInfo);
    Product product = productPersistenceService.getOrCreateProduct(mappedProduct);

    ProductViewLog productViewLog = new ProductViewLog();
    productViewLog.setProduct(product);
    productViewLog.setTimeOfViewing(mapTimestampToLocalDateTime(productView.getTimestamp()));

    return productViewLog;
  }
}
