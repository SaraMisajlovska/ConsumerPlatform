package mk.ukim.finki.thesis.common.serializer;

import mk.ukim.finki.thesis.common.enums.MessageKey;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

/**
 * Used for kafka message key deserialization since a custom enum, {@link MessageKey}, is used as a key.
 */
public class MessageKeyDeserializer implements Deserializer<MessageKey> {

  @Override
  public MessageKey deserialize(String topic, byte[] data) {
    if (data == null || data.length == 0) {
      return null;
    }
    String keyString = new String(data, StandardCharsets.UTF_8);
    return MessageKey.fromString(keyString);
  }
}
