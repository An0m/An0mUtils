package dev.an0m.mcutils;

import net.md_5.bungee.api.ChatColor;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    /**
     * Translates the basic Minecraft color codes to actual colors
     * @param message The message to "translate"
     * @return The colored version of the message
     */
    public static String cc(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    /**
     * Translates the hex color codes of a message to actual colors (can be seen only in newer versions)
     * @param message The message to "translate"
     * @return The colored version of the message
     */
    public static String translateHexColorCodes(final String message) {
        final char colorChar = ChatColor.COLOR_CHAR;

        final Matcher matcher = HEX_PATTERN.matcher(message);
        final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
    }

    /**
     * Merges the functions cc and translateHexColorCodes.
     * I would suggest using a version that also supports PlaceHolderAPI, just to add everything
     * @param message The message to "translate"
     * @return The colored message
     */
    public static String colorTranslate(String message) { //TODO: Also add support for placeholder API... or maybe not
        return cc(translateHexColorCodes(message));
    }


    /** Compacts an array of strings. Like join, but slightly better */
    public static String compactStringArray(String[] strings, int startFrom, String space) {
        if (startFrom >= strings.length) return "";
        String[] processed;

        if (startFrom != 0) {
            processed = new String[strings.length - startFrom];
            System.arraycopy(strings, startFrom, processed, 0, strings.length - startFrom);
        } else processed = strings;

        return String.join(space, processed);
    }
    /** Compacts an array of strings. Like join, but slightly better */
    public static String compactStringArray(String[] strings, int startFrom) {
        return compactStringArray(strings, startFrom, " ");
    }
    /** Compacts an array of strings. Like join, but slightly better */
    public static String compactStringArray(String[] strings, String space) {
        return compactStringArray(strings, 0, space);
    }
    /** Compacts an array of strings. Like join, but slightly better */
    public static String compactStringArray(String[] strings) {
        return compactStringArray(strings, 0);
    }

    /** Compacts an array of strings. Like join, but slightly better */
    public static String compactStringArray(Collection<String> strings, int startFrom, String space) {
        return compactStringArray(strings.toArray(new String[0]), startFrom, space);
    }
    /** Compacts an array of strings. Like join, but slightly better */
    public static String compactStringArray(Collection<String> strings, int startFrom) {
        return compactStringArray(strings, startFrom, " ");
    }
    /** Compacts an array of strings. Like join, but slightly better */
    public static String compactStringArray(Collection<String> strings, String space) {
        return compactStringArray(strings, 0, space);
    }
    /** Compacts an array of strings. Like join, but slightly better */
    public static String compactStringArray(Collection<String> strings) {
        return compactStringArray(strings, 0);
    }


}
