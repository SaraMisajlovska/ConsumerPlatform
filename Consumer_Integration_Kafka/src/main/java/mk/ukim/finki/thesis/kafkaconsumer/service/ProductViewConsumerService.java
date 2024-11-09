package mk.ukim.finki.thesis.kafkaconsumer.service;

import ecommerce.ProductView;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductViewConsumerService {

  @KafkaListener(topics = "${kafka.topic.producer-one.user-actions}", groupId = "thesis-consumer")
  public void consumeMessage(ConsumerRecord<String, ProductView> consumerRecord) {

    log.info("Consumed message:{} ", consumerRecord.value());
  }
}
