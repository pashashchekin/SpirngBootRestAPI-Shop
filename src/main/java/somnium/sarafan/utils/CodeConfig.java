package somnium.sarafan.utils;

import java.util.Arrays;
import java.util.Random;

public class CodeConfig {

    public final static char PATTERN_PLACEHOLDER = '#';

    public static class Charset {
        public static final String ALPHABETIC   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String ALPHANUMERIC = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String NUMBERS      = "0123456789";
    }

    private final int length;
    private final String charset;
    private final String prefix;
    private final String postfix;
    private final String pattern;

    public CodeConfig(Integer length, String charset, String prefix, String postfix, String pattern) {
        if (length == null) {
            length = 8;
        }

        if (charset == null) {
            charset = Charset.ALPHANUMERIC;
        }

        if (pattern == null) {
            char[] chars = new char[length];
            Arrays.fill(chars, PATTERN_PLACEHOLDER);
            pattern = new String(chars);
        }

        this.length = length;
        this.charset = charset;
        this.prefix = prefix;
        this.postfix = postfix;
        this.pattern = pattern;
    }

    public static String generate(CodeConfig config) {
        final Random RND = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        char[] chars = config.getCharset().toCharArray();
        char[] pattern = config.getPattern().toCharArray();

        if (config.getPrefix() != null) {
            sb.append(config.getPrefix());
        }

        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] == CodeConfig.PATTERN_PLACEHOLDER) {
                sb.append(chars[RND.nextInt(chars.length)]);
            } else {
                sb.append(pattern[i]);
            }
        }
        if (config.getPostfix() != null) {
            sb.append(config.getPostfix());
        }
        return sb.toString();
    }

    public static CodeConfig length(int length) {
        return new CodeConfig(length, null, null, null, null);
    }

    public static CodeConfig pattern(String pattern) {
        return new CodeConfig(null, null, null, null, pattern);
    }

    public int getLength() {
        return length;
    }

    public String getCharset() {
        return charset;
    }

    public CodeConfig withCharset(String charset) {
        return new CodeConfig(length, charset, prefix, postfix, pattern);
    }

    public String getPrefix() {
        return prefix;
    }

    public CodeConfig withPrefix(String prefix) {
        return new CodeConfig(length, charset, prefix, postfix, pattern);
    }

    public String getPostfix() {
        return postfix;
    }

    public CodeConfig withPostfix(String postfix) {
        return new CodeConfig(length, charset, prefix, postfix, pattern);
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return "CodeConfig ["
                + "length="  + length  + ", "
                + "charset=" + charset + ", "
                + "prefix="  + prefix  + ", "
                + "postfix=" + postfix + ", "
                + "pattern=" + pattern + "]";
    }
}