package mk.ukim.finki.thesis.kafkaconsumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.thesis.common.enums.MessageKey;
import mk.ukim.finki.thesis.kafkaconsumer.handler.RecordHandler;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {

  private final List<RecordHandler> recordHandlers;

  @KafkaListener(topics = "${kafka.topic.producer-one.user-actions}", groupId = "thesis-consumer")
  public void consumeMessage(ConsumerRecord<MessageKey, ? extends SpecificRecord> consumerRecord) {

    MessageKey key = consumerRecord.key();
    SpecificRecord value = consumerRecord.value();

    recordHandlers.stream()
            .filter(recordHandler -> recordHandler.handles(key))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No handler found for key: " + key))
            .handle(value);

    log.info("Consumed message with key {} and value {}", key, value);
  }
}
