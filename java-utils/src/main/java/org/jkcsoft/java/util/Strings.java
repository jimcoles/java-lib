/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Collection of String utilities.
 *
 * @author
 * @version 1.0
 */
public class Strings {
    
    private static final Logger log = LoggerFactory.getLogger(Strings.class);
    
    public static final Lister TO_STRING_LISTER = new ToStringLister();
    private static final String COMMA = ",";
    
    //----------------------------------------------------------------------------
    // Public Static methods
    //----------------------------------------------------------------------------
    private static final Cache<String, MessageFormat> memoizationCache =
        CacheBuilder.newBuilder()
                    .maximumSize(100) // Set the maximum cache size
                    .build();
    
    /**
     * Does string substitution of substrings with args.
     */
    public static String fmt(String template, Object... args) {
        MessageFormat format = null;
        try {
            // Get the result from the cache or compute it if not present
            format = memoizationCache.get(template, () -> new MessageFormat(template));
        }
        catch (ExecutionException e) {
            log.error("error computing string formatter for ["+template+"]" , e);
            format = new MessageFormat(template);
        }
        return format.format(args);
    }
    
    public static String replaceAll(String in, String find, String replace) {
        String retVal = in;
        if (in != null && find != null) {
            int lenMatch = find.length();
            StringBuilder sb = new StringBuilder(in.length());
            if (replace == null)
                replace = "";
            int idxOccurencePosition = 0, idxInStartSegment = 0;
            idxOccurencePosition = in.indexOf(find, idxInStartSegment);
            while (idxOccurencePosition >= 0) {
                sb.append(
                    in.substring(idxInStartSegment, idxOccurencePosition));
                sb.append(replace);
                idxInStartSegment = idxOccurencePosition + lenMatch;
                idxOccurencePosition = in.indexOf(find, idxInStartSegment);
            }
            sb.append(in.substring(idxInStartSegment));
            retVal = sb.toString();
        }
        return retVal;
    }
    
    /**
     * Returns true if the email string contains an at sign ("@") and
     * at least one dot ("."), i.e. "hans@gefionsoftware.com" is accepted
     * but "hans@gefionsoftware" is not. Note! This rule is not always
     * correct (e.g. on an intranet it may be okay with just a name) and
     * does not gurantee a valid Internet email address but it takes
     * care of the most obvious Internet mail address format errors.
     *
     * @param emailAddrString a String representing an email address
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmailAddr(String emailAddrString) {
        boolean isValid = false;
        if (emailAddrString != null
            && emailAddrString.indexOf("@") != -1
            && emailAddrString.indexOf(".") != -1) {
            isValid = true;
        }
        return isValid;
    }
    
    /**
     * Returns true if the specified string matches a string in the set
     * of provided valid strings, ignoring case if specified.
     *
     * @param value        the String validate
     * @param validStrings A sorted array of valid Strings
     * @param ignoreCase   if true, case is ignored when comparing the value
     *                     to the set of validStrings
     * @return true if valid, false otherwise
     */
    public static boolean contains(
        String value,
        String[] validStrings,
        boolean ignoreCase)
    {
        if (ignoreCase) {
            return (
                java.util.Arrays.binarySearch(
                    validStrings,
                    value,
                    String.CASE_INSENSITIVE_ORDER)
                    >= 0);
        }
        else {
            return (java.util.Arrays.binarySearch(validStrings, value) >= 0);
        }
    }
    
    public static boolean contains(String in, char[] chars) {
        Arrays.sort(chars);
        boolean retVal = false;
        if (in != null) {
            char caIn[] = in.toCharArray();
            Arrays.sort(caIn);
            for (int idx = 0; idx < in.length(); idx++) {
                if (Arrays.binarySearch(chars, caIn[idx]) >= 0) {
                    retVal = true;
                    break;
                }
            }
        }
        return retVal;
    }
    
    public static <T> String buildCommaDelList(Collection<T> items, Lister<T> lister) {
        if (items != null)
            return buildDelList(items, lister, COMMA + " ");
        else
            return "";
    }
    
    /**
     * buildCommaDelList() builds a comma delimited list
     * from an Vector of Objects.
     */
    public static <T> String buildCommaDelList(Collection<T> items) {
        if (items != null)
            return buildDelList(items, new ToStringLister<T>(), COMMA + " ");
        else
            return "";
    }
    
    public static <T> String buildNewlineList(Collection<T> items) {
        if (items != null)
            return buildDelList(items, new ToStringLister<T>(), JavaHelper.EOL);
        else
            return "";
    }
    
    /** The most general form. */
    public static <T> String buildDelList(Collection<T> items, Lister<T> lister, String delimiter) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        if (items != null) {
            for (T item : items) {
                count++;
                if (item != null) {
                    sb.append(lister.getListString(item));
                    if (count < items.size()) {
                        sb.append(delimiter);
                    }
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * Returns everything to the right of the first occurence of <code>sub</code>.
     */
    public static String restAfterFirst(String input, String sub) {
        return rest(input, sub, true);
    }
    
    /**
     * Returns everything to the right of the last occurence of <code>sub</code>.
     * Good for getting Class name from fully-qualified name.
     */
    public static String restAfterLast(String input, String sub) {
        return rest(input, sub, false);
    }
    
    public static String rest(String input, String sub, boolean first) {
        String retString = null;
        if (input != null) {
            int lastIdx = (
                first ? input.lastIndexOf(sub) : input.lastIndexOf(sub));
            if (lastIdx != -1)
                retString = input.substring(lastIdx + sub.length());
            else
                retString = input;
        }
        return retString;
    }
    
    /**
     * takes a comma delimited list of longs (String) and returns the last long
     */
    public static long getLastLong(String strIDs)
        throws NumberFormatException
    {
        return getLastLong(strIDs, ',');
    } //end getLastLong
    
    public static long getLastLong(String strIDs, char delim)
        throws NumberFormatException
    {
        long lngID = -1;
        if (strIDs == null)
            return lngID;
        if (strIDs.equals(""))
            return lngID;
        if (strIDs.lastIndexOf(delim) != -1)
            lngID =
                Long.parseLong(
                    strIDs.substring(
                        strIDs.lastIndexOf(delim) + 1,
                        strIDs.length()));
        else
            lngID = Long.parseLong(strIDs);
        return lngID;
    } //end getLastLong
    
    /**
     * same thing only ints
     */
    public static int[] stringListToIntArray(String inStr)
        throws NumberFormatException
    {
        return stringListToIntArray(inStr, ",");
    }
    
    public static int[] stringListToIntArray(String inStr, String delim)
        throws NumberFormatException
    {
        
        if (inStr == null)
            return null;
        
        StringTokenizer st = new StringTokenizer(inStr, delim);
        int[] ret = new int[st.countTokens()];
        int idx = 0;
        while (st.hasMoreTokens()) {
            ret[idx] = Integer.parseInt(st.nextToken().trim());
        }
        return ret;
    }
    
    public static int[] stringArrayToIntArray(String[] inStr)
        throws NumberFormatException
    {
        
        if (inStr == null)
            return null;
        
        int[] ret = new int[inStr.length];
        for (int idxStr = 0; idxStr < inStr.length; idxStr++) {
            String str = inStr[idxStr];
            ret[idxStr] = Integer.parseInt(str);
        }
        return ret;
    }
    
    /**
     * takes a comma separated list of longs and returns an array of longs
     * this method at this point assumes correctly formatted input
     */
    public static long[] stringListToLongArray(String inStr)
        throws NumberFormatException
    {
        return stringListToLongArray(inStr, ",");
    }
    
    public static long[] stringListToLongArray(String inStr, String delim)
        throws NumberFormatException
    {
        StringTokenizer st = new StringTokenizer(inStr, delim);
        long[] ret = new long[st.countTokens()];
        int idx = 0;
        while (st.hasMoreTokens()) {
            ret[idx] = Long.parseLong(st.nextToken().trim());
        }
        return ret;
    }
    
    public static String[] stringListToStringArray(String inStr)
        throws NumberFormatException
    {
        return stringListToStringArray(inStr, ",");
    }
    
    public static String[] stringListToStringArray(String inStr, String delim)
        throws NumberFormatException
    {
        StringTokenizer st = new StringTokenizer(inStr, delim);
        String[] ret = new String[st.countTokens()];
        int idx = 0;
        while (st.hasMoreTokens()) {
            ret[idx] = st.nextToken().trim();
            idx++;
        }
        return ret;
    }
    
    static public String replaceDashByUnderScore(String s) {
        return s.replace('-', '_');
    }
    
    static public boolean isValidIntOrLong(String s) {
        boolean ret = false;
        try {
            Long.parseLong(s); // if it parses w/o exception, it's valid.
            ret = true;
        }
        catch (NumberFormatException ignore) {
        }
        return ret;
    }
    
    static public boolean isFloat(String s) {
        boolean ret = false;
        try {
            Float.parseFloat(s); // if it parses w/o exception, is a float.
            ret = true;
        }
        catch (NumberFormatException ignore) {
        }
        return ret;
    }
    
    static public boolean isNumeric(String s) {
        return isFloat(s);
    }
    
    public static boolean isPrintable(String s) {
        if (s == null || s.equals("") || s.equals("null") || s.equals(""))
            return false;
        else
            return true;
    }
    
    /**
     * fixDoubleQuotes takes a String and if it has a double quote in it, inserts a \
     */
    public static String escapeQuotes(String str) {
        if (str == null)
            return null;
        StringBuilder jdtSB = new StringBuilder(str);
        for (int i = jdtSB.length() - 1; i >= 0; --i)
            if (jdtSB.charAt(i) == '\'' || jdtSB.charAt(i) == '\"')
                jdtSB.insert(i, '\\');
        return jdtSB.toString();
    } //end fixDoubleQuotes
    
    /**
     * Trims with a null check.
     */
    static public String trimSafe(String s) {
        if (s == null)
            return null;
        return s.trim();
    }
    
    /**
     * Returns string limited to specified length
     */
    
    static public String limit(String s, int maxLength) {
        return limit(s, maxLength, "..");
    }
    
    static public String limit(String s, int maxLength, String truncationIndicator) {
        String retString = null;
        if (s == null) {
            retString = null;
        }
        else if (s.length() <= maxLength) {
            retString = s;
        }
        else {
            retString = s.substring(0, maxLength) + truncationIndicator;
        }
        return retString;
    }
    
    /**
     * takes a comma separated list of longs and returns a list of Longs
     * this method at this point assumes correctly formatted input
     */
    public static List stringListToLongList(String inStr)
        throws NumberFormatException
    {
        StringTokenizer st = new StringTokenizer(inStr, ",");
        List ret = new LinkedList();
        while (st.hasMoreTokens()) {
            ret.add(Long.parseLong(st.nextToken().trim()));
        }
        return ret;
    }
    
    /**
     *
     */
    public static boolean isInt(String numStr) {
        try {
            Integer.parseInt(numStr);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

//    private static Map<String, String> multStringMap = new HashMap<>();
    
    /**
     *
     */
    public static String multiplyString(String baseStr, int num) {
        return multiplyString(baseStr, num, null);
    }
    
    /**
     * @param baseStr     the string we want multiple copies of
     * @param num         the number of copies of baseStr
     * @param multiPrefix optional prefix of repeating cycle after the first
     */
    public static String multiplyString(String baseStr, int num, String multiPrefix) {
        String computedString = "";
        StringBuilder buf = new StringBuilder(baseStr.length() * num);
        for (int idx = 0; idx < num; idx++) {
            if (multiPrefix != null && idx > 0)
                buf.append(multiPrefix);
            buf.append(baseStr);
        }
        computedString = buf.toString();
        return computedString;
    }
    
    /**
     * Return toString() if its non-null, non-empty; otherwise returns specified
     * default value <code>def</code>.
     */
    public static String toStringDef(Object obj, String def) {
        if (obj == null)
            return def;
        String tostring = obj.toString();
        if (tostring == null)
            return def;
        if (tostring.trim().length() == 0)
            return def;
        return obj.toString();
    }
    
    /**
     * Returns false if the String provided is equals to null, has a
     * length equal to 0
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }
    
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }
    
    /**
     * Returns boolean if any of the IP octets is not valid and does not match the number
     * of octets provided
     */
    public static boolean isValidIp(String ip, int octetCount) {
        if (Strings.isEmpty(ip)) {
            throw new IllegalArgumentException("The IP can't be null");
        }
        if (octetCount < 1 || octetCount > 4) {
            throw new IllegalArgumentException("The octet count must be between 1 and 4");
        }
        
        boolean valid = true;
        
        int countIpOctets = 0;
        StringBuilder sb = new StringBuilder(ip);
        //If the ip is subnet and does not end with a ".", then add one.
        if ((ip.lastIndexOf(".") + 1 != ip.length())) {
            sb.append(".");
        }
        
        int index = 0;
        int indexZero = 0;
        while ((index = sb.indexOf(".")) != -1) {
            
            String octet = sb.substring(indexZero, index);
            
            try {
                if (Integer.parseInt(octet) > 255
                    || Integer.parseInt(octet) < 0) {
                    valid = false;
                    return valid;
                }
            }
            catch (NumberFormatException e) {
                valid = false;
                return valid;
            }
            
            sb.delete(indexZero, index + 1); //delete octet and it's '.'
            
            countIpOctets++;
        }
        if (countIpOctets != octetCount) {
            valid = false;
            return valid;
        }
        
        return valid;
    }
    
    /**
     * Gets String between two delimiting Strings
     */
    public static String cutBetween(
        String str,
        String startIndexString,
        String endIndexString)
    {
        if (str == null || startIndexString == null)
            return null;
        
        int beginIndex = -1;
        if ((beginIndex = str.indexOf(startIndexString)) == -1) {
            return "";
        }
        beginIndex += startIndexString.length();
        
        int endIndex =
            (endIndexString != null)
                ? str.indexOf(endIndexString, beginIndex)
                : str.length();
        if (endIndex == -1) {
            endIndex = str.length();
        }
        
        String cutStr = null;
        try {
            cutStr = str.substring(beginIndex, endIndex);
        }
        catch (Exception e) {
//            LogUtil.LOG_WEB.error(e);
            cutStr = "?";
        }
        
        return cutStr;
    }
    
    public static String convert8859ToUtf8(String in) {
        if (in == null)
            return null;
        
        String out = convertEnc(in, "ISO-8859-1", "UTF-8");
        return out;
    }
    
    public static String convertEnc(String in, String fromEnc, String toEnc) {
        if (in == null)
            return null;
        
        String out = null;
        try {
            out = new String(in.getBytes(fromEnc), toEnc);
        }
        catch (UnsupportedEncodingException e) {
            out = e.getMessage() + "??";
        }
        return out;
    }
    
    public static String escapeUnicodeString(String str, boolean escapeAscii) {
        if (str == null)
            return null;
        
        String ostr = new String();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!escapeAscii && ((ch >= 0x0020) && (ch <= 0x007e)))
                ostr += ch;
            else {
                ostr += "\\u";
                String hex = Integer.toHexString(str.charAt(i) & 0xFFFF);
                if (hex != null) {
                    if (hex.length() == 2)
                        ostr += "00";
                    ostr += hex.toUpperCase(Locale.ENGLISH);
                }
            }
        }
        return (ostr);
    }
    
    /**
     * For all occurences of the keywords in baseString, set the prefix and suffix before and after the keyword.
     *
     * @param baseString
     * @param keywords
     * @param prefix
     * @param suffix
     * @return String
     */
    public static String markupSubstring(
        String baseString,
        String[] keywords,
        String prefix,
        String suffix)
    {
        if (keywords == null || keywords.length < 1) {
            return baseString;
        }
        
        String returnSring = baseString;
        for (int i = 0, l = keywords.length; i < l; i++) {
            returnSring =
                markupSubstring(returnSring, keywords[i], prefix, suffix);
        }
        
        return returnSring;
    }
    
    /**
     * For all occurences of keyword in baseString, set the prefix and suffix before and after the keyword.
     *
     * @param baseString
     * @param keyword
     * @param prefix
     * @param suffix
     * @return String
     */
    public static String markupSubstring(
        String baseString,
        String keyword,
        String prefix,
        String suffix)
    {
        if (Strings.isEmpty(keyword)) {
            return baseString;
        }
        String baseStringLowered = baseString.toLowerCase();
        String keywordLowered = keyword.toLowerCase();
        
        if (baseStringLowered.indexOf(keywordLowered) == -1) {
            return baseString;
        }
        int searchedUpToIndex = 0;
        StringBuilder returnString = new StringBuilder();
        
        while (baseStringLowered
            .substring(searchedUpToIndex, baseStringLowered.length())
            .indexOf(keywordLowered)
            != -1) {
            int beginOfKeyword =
                baseStringLowered
                    .substring(searchedUpToIndex, baseStringLowered.length())
                    .indexOf(keywordLowered);
            
            String textBeforeKeyword =
                baseString.substring(
                    searchedUpToIndex,
                    searchedUpToIndex + beginOfKeyword);
            returnString.append(textBeforeKeyword);
            
            String appendKeyword =
                baseString.substring(
                    searchedUpToIndex + beginOfKeyword,
                    searchedUpToIndex + beginOfKeyword + keyword.length());
            //textBeforeKeyword.length()
            returnString.append(prefix);
            returnString.append(appendKeyword);
            returnString.append(suffix);
            
            searchedUpToIndex =
                searchedUpToIndex + beginOfKeyword + keyword.length();
        }
        if (baseStringLowered.length() != searchedUpToIndex) {
            returnString.append(
                baseString.substring(
                    searchedUpToIndex,
                    baseStringLowered.length()));
        }
        
        return returnString.toString();
    }
    
    public static void appendLine(StringBuilder sbMsg, String s) {
        sbMsg.append(s + JavaHelper.EOL);
    }
    
    public static class ToStringLister<T> implements Lister<T> {
        public String getListString(T obj) {
            return "" + obj;
        }
    }
}