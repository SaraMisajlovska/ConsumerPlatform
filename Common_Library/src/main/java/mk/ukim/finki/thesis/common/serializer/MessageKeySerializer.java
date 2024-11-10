package mk.ukim.finki.thesis.common.serializer;

import mk.ukim.finki.thesis.common.enums.MessageKey;
import org.apache.kafka.common.serialization.Serializer;
import java.nio.charset.StandardCharsets;

/**
 * Used for kafka message key serialization since a custom enum, {@link MessageKey}, is used as a key.
 */
public class MessageKeySerializer implements Serializer<MessageKey> {

  @Override
  public byte[] serialize(String topic, MessageKey data) {
    return data != null ? data.toString().getBytes(StandardCharsets.UTF_8) : null;
  }
}
