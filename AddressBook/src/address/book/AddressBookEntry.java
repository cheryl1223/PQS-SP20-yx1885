package address.book;

import address.book.pojo.EmailAddress;
import address.book.pojo.Name;
import address.book.pojo.PhoneNumber;
import address.book.pojo.PostalAddress;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * This class provides Setter, Getter, Builder and toString methods for AddressBookEntry class, 
 * which consists of a name, a phoneNumber, a postalAddress, an emailAddress and a note.
 * The format of input string is validated in AddressBookEntry.Builder class. Only input with
 * required format will be accepted to construct AddressBookEntry object.
 *
 * @author cheryl
 *
 */
@Setter
@Getter
@ToString
public class AddressBookEntry {
  private static final Logger LOGGER = Logger.getLogger(AddressBookEntry.class.getName());
  private static final AddressBookValidator VALIDATOR = new AddressBookValidator();

  private final Name name;
  private final PhoneNumber phoneNumber;
  private final PostalAddress postalAddress;
  private final EmailAddress emailAddress;
  private final String note;


  public static class Builder {
    private Name name;
    private PhoneNumber phoneNumber;
    private PostalAddress postalAddress;
    private EmailAddress emailAddress;
    private String note;
    
    public Builder() {
      
    }

    /**
     * Validate and set name field of the entry builder.
     * @param name the Name object to initialize the name field of the entry builder
     * @return Builder the Builder object of the entry
     */
    public Builder setName(@NonNull final Name name) {
      LOGGER.info(String.format("Set name: %s", name.toString()));
      try {
        if (name.getFirstName() != null) {
          VALIDATOR.checkIfAlphaNumeric(name.getFirstName());
        }
        if (name.getLastName() != null) {
          VALIDATOR.checkIfAlphaNumeric(name.getLastName());
        }
      } catch (IllegalArgumentException e) {
        LOGGER.info(String.format("%s is an invalid input", name));
        return this;
      }
      this.name = name;
      return this;
    }
    
    /**
     * Validate and set phoneNumber field of the entry builder.
     * @param phoneNumber the PhoneNumber object to initialize the phoneNumber field
     * @return Builder the Builder object of the entry
     */
    public Builder setPhoneNumber(@NonNull final PhoneNumber phoneNumber) {
      LOGGER.info(String.format("Set phone number: %s", phoneNumber.toString()));
      try {
        if (phoneNumber.getPhoneNumber() != null) {
          VALIDATOR.checkIfAlphaNumeric(phoneNumber.getPhoneNumber());
        }
      } catch (IllegalArgumentException e) {
        LOGGER.info(String.format("%s is an invalid input", phoneNumber));
        return this;
      }
      this.phoneNumber = phoneNumber;
      return this;
    }

    /**
     * Validate and set postalAddress field of the entry builder.
     * @param postalAddress the PostalAddress object to initialize the postalAddress field 
     * @return Builder the Builder object of the entry
     */
    public Builder setPostalAddress(@NonNull final PostalAddress postalAddress) {
      LOGGER.info(String.format("Set postal address: %s", postalAddress.toString()));
      try {
        if (postalAddress.getStreet1() != null) {
          VALIDATOR.checkIfAlphaNumeric(postalAddress.getStreet1());
        }
        if (postalAddress.getStreet2() != null) {
          VALIDATOR.checkIfAlphaNumeric(postalAddress.getStreet2());
        }
      } catch (IllegalArgumentException e) {
        LOGGER.info(String.format("%s is an invalid input", postalAddress));
        return this;
      }
      this.postalAddress = postalAddress;
      return this;
    }

    /**
     * Set emailAddress field of the entry builder.
     * @param emailAddress the EmailAddress object to initialize the emailAddress field 
     * @return Builder the Builder object of the entry
     */
    public Builder setEmailAddress(@NonNull final EmailAddress emailAddress) {
      LOGGER.info(String.format("Set email address: %s", emailAddress.toString()));
      this.emailAddress = emailAddress;
      return this;
    }
    
    /**
     * Set note field of the entry builder.
     * @param note the string to initialize the note field 
     * @return Builder the Builder object of the entry
     */
    public Builder setNote(@NonNull final String note) {
      LOGGER.info(String.format("Set email address: %s", note));
      this.note = note;
      return this;
    }
    
    /**
     * Construct the AddressBookEntry class according to the properties of the builder.
     * @return AddressBookEntry initialize class according to the properties of the builder
     */
    public AddressBookEntry build() {
      return new AddressBookEntry(this);
    }
    
    
  }
  
  private AddressBookEntry(final Builder builder) {
    LOGGER.info("Construct a new AddressBookEntry");
    this.name = builder.name;
    this.phoneNumber = builder.phoneNumber;
    this.postalAddress = builder.postalAddress;
    this.emailAddress = builder.emailAddress;
    this.note = builder.note;
  }
  
  /**
   * Check if all fields of AddressBookEntry is null.
   * @return True if AddressBookEntry is empty; False if is not empty.
   */
  public boolean isEmpty() {
    if (this.name == null && this.phoneNumber == null
        && this.postalAddress == null && this.emailAddress == null
        && this.note == null) { 
      return true;
    }
    return false;
  }


}
