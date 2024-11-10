package mk.ukim.finki.thesis.kafkaconsumer.handler;

import mk.ukim.finki.thesis.common.enums.MessageKey;
import org.apache.avro.specific.SpecificRecord;

public interface RecordHandler {

  boolean handles(MessageKey key);

  void handle(SpecificRecord record);
}
