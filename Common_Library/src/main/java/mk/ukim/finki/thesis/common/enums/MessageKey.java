package mk.ukim.finki.thesis.common.enums;

public enum MessageKey {
  PRODUCT_VIEW,
  ADD_TO_CART,
  CART_ABANDONED,
  SEARCH_QUERY;

  public static MessageKey fromString(String value) {

    if (value == null || value.isEmpty()) {
      throw new IllegalArgumentException("Value cannot be null or empty.");
    }

    try {
      return MessageKey.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid MessageKey: " + value, e);
    }
  }
}
