import address.book.AddressBookController;
import address.book.AddressBookEntry;
import address.book.pojo.EmailAddress;
import address.book.pojo.Name;
import address.book.pojo.PhoneNumber;
import address.book.pojo.PostalAddress;

/**
 * This is a TestAddressBook class for testing and showing the usage of AddressBook package.
 * @author cheryl
 *
 */
public class TestAddressBook {
  /**
   * main class for testing.
   *
   */
  public static void main(String[] args) {
    //Create an empty address book
    AddressBookController book = new AddressBookController();
    
    //Add an entry consisting of a name, postal address, phone number, email address, and a note.
    AddressBookEntry entry1 = new AddressBookEntry.Builder()
        .setName(Name.builder().firstName("yuxiao").lastName("xie").build())
        .setEmailAddress(EmailAddress.builder().emailAddress("123@gmail.com").build())
        .setPhoneNumber(PhoneNumber.builder().phoneNumber("123321").build())
        .setPostalAddress(PostalAddress.builder().street1("first ave").postcode("10001").build())
        .build();
    book.addEntry(entry1);
    
    //Add more entries with different parameters.
    AddressBookEntry entry2 = new AddressBookEntry.Builder()
        .setName(Name.builder().firstName("Cheryl").build())
        .build();
    book.addEntry(entry2);
    
    AddressBookEntry entry3 = new AddressBookEntry.Builder()
        .setName(Name.builder().firstName("xie").lastName("xie").build())
        .setPhoneNumber(PhoneNumber.builder().phoneNumber("22222").build())
        .build();
    book.addEntry(entry3);

    AddressBookEntry entry4 = new AddressBookEntry.Builder()
        .setPhoneNumber(PhoneNumber.builder().phoneNumber("11111").build())
        .build();
    book.addEntry(entry4);
    
    AddressBookEntry entry5 = new AddressBookEntry.Builder()
        .setNote("12345")
        .build();
    book.addEntry(entry5);
    
    // Remove an entry
    System.out.println(String.format("The AddressBookEntry list length is : %d", 
        book.getBook().size())); //5
    book.removeEntry(3);
    System.out.println(String.format("After remove one entry, "
        + "the AddressBookEntry list length is : %d", 
        book.getBook().size())); //4

    // Search entries
    System.out.println(book.searchByName("xie"));//first and third entry
    System.out.println(book.searchIndexByName("xie")); //[0, 2]
    System.out.println(book.searchByOtherFields("first")); //first entry
    System.out.println(book.searchByName("?").size()); //0
    
    
    // Save the address book to a file.
    book.saveToJson("/Users/cheryl/pqs/1.json");
    
    // Read the address book from a file.
    AddressBookController book2 = new AddressBookController();
    book2.readFromJson("/Users/cheryl/pqs/1.json");
    System.out.println(book.toString().equals(book2.toString())); // true
    
    // file path not found.
    book2.readFromJson("/Users/cheryl/pqs/1");

  }
}
