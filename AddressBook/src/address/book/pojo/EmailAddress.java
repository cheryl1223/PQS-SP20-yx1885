package address.book.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class provides Setter, Getter, Builder and toString methods for POJO EmailAddress, 
 * which consists of emailAddress and label string.
 *
 */
@Builder
@Setter
@Getter
@ToString
public class EmailAddress {
  private final String emailAddress;
  private final String label; 
}
