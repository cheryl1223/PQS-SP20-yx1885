package address.book;

import java.util.regex.Pattern;

/**
 * This is the interface for AddressBookValidator, which provides extendable 
 * interfaces for the validator to implement. Future validate methods can be
 * easily added or overrided to the interface.
 * @author cheryl
 *
 */
public class AddressBookValidator {
  private static Pattern alphaNumericPattern = Pattern.compile("^[a-zA-Z0-9]*$");

  /**
   * Check if String s is consist of alphabet or numbers (A-Z, a-z, 0-9).
   *  WhiteSpaces are ignored.
   * @param s the string to be checked
   * @throw IllegalArgumentException if s is nonAlphaNumeric
   */
  public void checkIfAlphaNumeric(final String s) {
    final String spaceReplaced = s.replaceAll("\\s","");
    if (!alphaNumericPattern.matcher(spaceReplaced).find()) {
      throw new IllegalArgumentException(String.format("%s conains illegal characters", s));
    }
  }

  /**
   * Check if String s matched regex pattern pattern. WhiteSpaces are ignored.
   * @param s the string to be checked
   * @param pattern the regex pattern to be checked with
   * @return True if s matches pattern; False otherwise
   */
  public boolean checkIfMatchesPattern(final String s, final String pattern) {
    final Pattern p = Pattern.compile(pattern);
    final String spaceReplaced = s.replaceAll("\\s","");
    return p.matcher(spaceReplaced).find();
  }

  /**
   * Check if String s is under the maximum.
   * @param s the string to be checked
   * @param maxLength the maximum length to compare with
   * @return True if s matches pattern; False otherwise
   */
  public boolean checkIfUnderMaxSize(final String s, final int maxLength) {
    return s.length() <= maxLength;
  }
}
