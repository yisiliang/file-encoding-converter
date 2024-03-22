package com.yisiliang.idea.plugins.converter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EncodingConstants {
    public static final int GB18030 = 0;
    public static final int BIG5 = 1;
    public static final int UTF8 = 2;
    public static final int UTF16BE = 3;
    public static final int UTF16LE = 4;
    public static final int UTF32BE = 5;
    public static final int UTF32LE = 6;
    public static final int EUC_KR = 7;
    public static final int CP949 = 8;
    public static final int EUC_JP = 9;
    public static final int ASCII = 10;
    public static final int OTHER = 11;
    public static final int TOTAL_TYPES = 12;

    // Names of the encodings as understood by Java
    public static final Charset[] SUPPORT_CHARSET_ARRAY;

    // Constructor
    static {
        SUPPORT_CHARSET_ARRAY = new Charset[TOTAL_TYPES];
        // Assign encoding names
        SUPPORT_CHARSET_ARRAY[GB18030] = Charset.forName("GB18030");
        SUPPORT_CHARSET_ARRAY[BIG5] = Charset.forName("BIG5");
        SUPPORT_CHARSET_ARRAY[UTF8] = StandardCharsets.UTF_8;
        SUPPORT_CHARSET_ARRAY[UTF16BE] = StandardCharsets.UTF_16BE;
        SUPPORT_CHARSET_ARRAY[UTF16LE] = StandardCharsets.UTF_16LE;
        SUPPORT_CHARSET_ARRAY[UTF32BE] = Charset.forName("UTF-32BE");
        SUPPORT_CHARSET_ARRAY[UTF32LE] = Charset.forName("UTF-32LE");
        SUPPORT_CHARSET_ARRAY[EUC_KR] = Charset.forName("EUC_KR");
        SUPPORT_CHARSET_ARRAY[CP949] = Charset.forName("MS949");
        SUPPORT_CHARSET_ARRAY[EUC_JP] = Charset.forName("EUC_JP");
        SUPPORT_CHARSET_ARRAY[ASCII] = StandardCharsets.US_ASCII;
        SUPPORT_CHARSET_ARRAY[OTHER] = Charset.forName("ISO8859_1");
    }
}
