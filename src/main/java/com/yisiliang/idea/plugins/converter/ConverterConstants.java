package com.yisiliang.idea.plugins.converter;

import java.nio.charset.Charset;
import java.util.List;

public class ConverterConstants {
    public static final Charset[] SUPPORT_CHARSET_ARRAY = new Charset[]{
            EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.UTF8],
            EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.GB18030]
    };
    public static final List<String> DEFAULT_SELECTED_FILE_TYPES = List.of("java");
    public static String MESSAGE_TITLE = "File-Encoding-Converter";
}
