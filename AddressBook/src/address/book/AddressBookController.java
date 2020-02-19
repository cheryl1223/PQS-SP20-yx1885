package address.book;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is the controller class for address book entries. It provides methods to
 * <li>Create an empty address book.
 * <li>Add an entry consisting of a name, postal address, phone number, email address, and a note.
 * <li>Remove an entry.
 * <li>Search entries.
 * <li>Save the address book to a file.
 * <li>Read the address book from a file.
 * 
 * @author cheryl
 *
 */
@ToString
@EqualsAndHashCode
public class AddressBookController {
  private static final Logger LOGGER = Logger.getLogger(AddressBookController.class.getName());
  
  /**
   * Gson object with pretty printing.
   */
  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

  /**
   * AddressBookEntry list to store the address book entries.
   */
  @Getter @Setter
  private List<AddressBookEntry> book;
  
  public AddressBookController() {
    this.book = new ArrayList<AddressBookEntry>();
  }
  
  public AddressBookController(final List<AddressBookEntry> entryList) {
    this.book = entryList;
  }
  
  /**
   * Append an entry to this.book.
   * @param entry the AddressBookEntry object that will be added to this.book
   */
  public void addEntry(final AddressBookEntry entry) {
    this.book.add(entry);
  }
  
  /**
   * Remove the entry at a specific index.
   * @param index the index of the entry to remove
   */
  public void removeEntry(final int index) {
    LOGGER.info(String.format("Remove entry at index %d ", index));
    try {
      this.book.remove(index);
    } catch (IndexOutOfBoundsException e) {
      LOGGER.warning(String.format("Catch IndexOutOfBoundsException, index %d is invalid", index));
    }
  }  
  
  private boolean containsIgnoreCase(final String text, final String token) {
    final String toLowerText = text.toLowerCase();
    final String toLowerToken = token.toLowerCase();
    return toLowerText.contains(toLowerToken);
  }
  
  /**
   * Search for the index of the entries according by Name field of the entry. If token is a 
   * substring of the entry's Name string, add the entry index to result. Case is 
   * ignored in the comparison.
   * @param token the substring to compare with 
   * @return List the list of index of matched entries
   */
  public List<Integer> searchIndexByName(final String token) {
    LOGGER.info(String.format("Search the index of entry by Name for %s", token));
    List<Integer> result = new ArrayList<Integer>();
    int i = 0;
    for (AddressBookEntry entry : this.book) {
      if (entry.getName() != null && containsIgnoreCase(entry.getName().toString(), token)) {
        result.add(i);
      }
      i++;
    }
    return result;
  }
  
  /**
   * Search for the entries according by Name field of the entry. If token is a 
   * substring of the entry's Name string, add the entry to result. Case is 
   * ignored in the comparison.
   * @param token the substring to compare with 
   * @return List the list of matched entries
   */  
  public List<AddressBookEntry> searchByName(final String token) {
    LOGGER.info(String.format("Search the entry by Name for %s", token));
    List<AddressBookEntry> result = new ArrayList<AddressBookEntry>();
    for (AddressBookEntry entry : this.book) {
      if (entry.getName() != null && containsIgnoreCase(entry.getName().toString(), token)) {
        result.add(entry);
      }
    }
    return result;
  }
  
  /**
   * Search for the entries according to the fields other the Name (e.g. PhoneNumber, PostalAddress,
   * etc.)of the entry. If token is a substring of the entry's Name string, add the entry to result.
   * Case is ignored in the comparison.
   * @param token the substring to compare with 
   * @return List the list of matched entries
   */ 
  public List<AddressBookEntry> searchByOtherFields(final String token) {
    LOGGER.info(String.format("Search fiedls other than Name for %s", token));
    List<AddressBookEntry> result = new ArrayList<AddressBookEntry>();
    for (AddressBookEntry entry : this.book) {
      if (entry.getEmailAddress() != null 
          && containsIgnoreCase(entry.getEmailAddress().toString(), token)
          || entry.getPhoneNumber() != null 
          && containsIgnoreCase(entry.getPhoneNumber().toString(), token)
          || entry.getPostalAddress() != null 
          && containsIgnoreCase(entry.getPostalAddress().toString(), token)
          || entry.getNote() != null 
          && containsIgnoreCase(entry.getNote(), token)) {
        result.add(entry);
      }
    }
    return result;
  }
  
  /**
   * Serialize AddressBookEntry list book in json format and save it to file.
   * @param savePath the output file path
   */
  public void saveToJson(final String savePath) {
    LOGGER.info(String.format("save to json: %s", savePath));
    try {
      final Type typeAddressBookEntry = new TypeToken<ArrayList<AddressBookEntry>>(){}.getType();
      String s = gson.toJson(this.getBook(),typeAddressBookEntry);
      LOGGER.info(s);
      Files.write(Paths.get(savePath), s.getBytes());
    } catch (IOException e) {
      LOGGER.info(String.format("Catch IOException, file %s not found", savePath));
    }
    
  }
  
  /**
   * Read the input file as string, deserialize the string to AddressBookEntry list, and update
   * this.book according to the deserialization result.
   * @param readPath the input file path
   */
  public AddressBookController readFromJson(final String readPath) {
    LOGGER.info(String.format("read from json: %s", readPath));
    String content = "";
    try {
      content = new String(Files.readAllBytes(Paths.get(readPath)));
      final Type typeAddressBookEntry = new TypeToken<ArrayList<AddressBookEntry>>(){}.getType();
      List<AddressBookEntry> bookList = gson.fromJson(content, typeAddressBookEntry);
      this.setBook(bookList);
    } catch (IOException e) {
      LOGGER.info(String.format("Catch IOException, file %s not found", readPath));
    }
    return this;
  }
  
}
