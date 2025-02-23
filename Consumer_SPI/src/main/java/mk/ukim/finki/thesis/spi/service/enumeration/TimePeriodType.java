package mk.ukim.finki.thesis.spi.service.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum which is a one-to-one copy of the filter in the frontend component.
 */
@Getter
@RequiredArgsConstructor
public enum TimePeriodType {

  date("dd-MM-yyyy"),
  month("MM-yyyy"),
  quarter("QQ-yyyy"),
  year("yyyy");

  /**
   * Pattern used to format the dates based on the filter type.
   */
  private final String formatPattern;
}
