package mk.ukim.finki.thesis.kafkaconsumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductViewConsumerService {

  @KafkaListener(topics = "${kafka.topic.producer-one.user-actions}", groupId = "thesis-consumer")
  public void consumeMessage(ConsumerRecord<String, ? extends SpecificRecord> consumerRecord) {

    log.info("Consumed message:{} ", consumerRecord.value());
  }
}
