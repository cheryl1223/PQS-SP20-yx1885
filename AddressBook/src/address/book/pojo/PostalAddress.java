package address.book.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class provides Setter, Getter, Builder and toString methods for POJO PostalAddress, 
 * which consists of street1, street2, city, state, postcode, country string.
 *
 */
@Builder
@Setter
@Getter
@ToString
public class PostalAddress {
  private final String street1;
  private final String street2;
  private final String city;
  private final String state;
  private final String postcode;
  private final String country;
}
