package address.book.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class provides Setter, Getter, Builder and toString methods for POJO PhoneNumber, 
 * which consists of phoneNumber and label string.
 *
 */
@Builder
@Setter
@Getter
@ToString
public class PhoneNumber {
  private final String phoneNumber;
  private final String label; 
}
