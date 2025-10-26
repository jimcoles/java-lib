package org.jkcsoft.java.util;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * A Charsets is
 *
 * @author Jim Coles
 */

public class Charsets {
    
    public static boolean isReadable(char ch) {
        // Reject control characters (e.g., ASCII control codes)
        if (Character.isISOControl(ch)) {
            return false;
        }
        
        // Reject unassigned characters
        if (!Character.isDefined(ch)) {
            return false;
        }
        
        // Get the Unicode category of the character
        int charType = Character.getType(ch);
        
        // Allow only certain categories of characters
        return switch (charType) {
            case Character.UPPERCASE_LETTER,   // Uppercase (A, B, ...)
                 Character.LOWERCASE_LETTER,   // Lowercase (a, b, ...)
                 Character.TITLECASE_LETTER,   // Titlecase letters
                 Character.MODIFIER_LETTER,   // Modifier letters (e.g., ˇ, ˘)
                 Character.OTHER_LETTER,      // Other letters (e.g., CJK ideograms)
                 Character.DECIMAL_DIGIT_NUMBER, // Decimal digits (0-9)
                 Character.LETTER_NUMBER,     // Letter-like numbers (e.g., Roman numerals)
                 Character.OTHER_NUMBER,      // Other numbers (e.g., fractions like ⅓)
                 Character.MATH_SYMBOL,       // Mathematical symbols (e.g., +, =, √)
                 Character.CURRENCY_SYMBOL,   // Currency symbols (€, $, ¥)
                 Character.OTHER_PUNCTUATION, // Other punctuation marks (e.g., %, &)
                 Character.DASH_PUNCTUATION,  // Dash punctuation (e.g., -, —, ‒)
                 Character.INITIAL_QUOTE_PUNCTUATION, // Opening quotes (e.g., “, ‘)
                 Character.FINAL_QUOTE_PUNCTUATION,   // Closing quotes (e.g., ”, ’)
                 Character.OTHER_SYMBOL -> true; // Symbols like emojis, arrows, etc.
            default -> false; // Reject everything else
        };
    }
    
    public static void predicates() {
        char c = 'A';
        System.out.println(Character.isLetter(c));           // true
        System.out.println(Character.isDigit(c));            // false
        System.out.println(Character.isWhitespace(c));       // false
        System.out.println(Character.isJavaIdentifierStart('_')); // true
        System.out.println(Character.isLetterOrDigit('8'));  // true
        
        char digit = '5';
        System.out.println(Character.isDigit(digit));              // true
        System.out.println(Character.getNumericValue(digit));      // 5
        
        char arabicDigit = '٥'; // Arabic-Indic digit 5
        System.out.println(Character.isDigit(arabicDigit));        // true
        System.out.println(Character.getNumericValue(arabicDigit)); // 5
    }
    
    public static void main(String[] args) {
        enumerateSupportedCharacters(StandardCharsets.UTF_8);
    }
    
    private static Set<Character> enumerateSupportedCharacters(final Charset charset) {
        Set<Character> supported = new HashSet<>();
        CharsetEncoder encoder = charset.newEncoder();
        int numReadableChars = 0, numCtrlChars = 0;
        for (int codePoint = 0; codePoint <= Character.MAX_CODE_POINT; codePoint++) {
            if (Character.isDefined(codePoint)) { // Skip undefined code points
                if (Character.isISOControl(codePoint)) {
                    numCtrlChars++;
                }
                char[] chars = Character.toChars(codePoint); // Convert to chars
                String charAsString = new String(chars);
                // Check if the character can be encoded in the charset
                if (encoder.canEncode(charAsString)) {
                    supported.add(chars[0]);
//                    System.out.printf("Code Point: U+%04X -> Character: %s%n", codePoint, charAsString);
                }
            }
        }
        
        return supported;
    }
}
