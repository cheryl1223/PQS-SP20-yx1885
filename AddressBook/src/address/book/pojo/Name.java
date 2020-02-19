package address.book.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class provides Setter, Getter, Builder and toString methods for POJO Name, 
 * which consists of fistName and lastName string.
 *
 */
@Builder
@Setter
@Getter
@ToString
public class Name {
  private final String firstName;
  private final String lastName; 
}
