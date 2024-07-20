package com.yisiliang.idea.plugins.converter;

import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Pattern;

public class ConverterConstants {
    public static final Charset[] SUPPORT_CHARSET_ARRAY = new Charset[]{
            EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.UTF8],
            EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.GB18030]
    };
    public static final String FILE_TYPE_JSP = "jsp";
    public static final String FILE_TYPE_XML = "xml";
    public static final String FILE_TYPE_HTML = "html";

    /**
     * GB编码家族
     */
    public static final Charset CHARSET_GBK = Charset.forName("GBK");
    public static final Charset CHARSET_GB2312 = Charset.forName("GB2312");
    public static final Charset CHARSET_CP936 = Charset.forName("CP936");
    public static final Charset CHARSET_GB18030 = EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.GB18030];
    public static final List<Charset> GB_CHARSET_LIST = List.of(CHARSET_GBK, CHARSET_GB2312, CHARSET_GB18030, CHARSET_CP936);

    public static final String GB_CHARSET_STRING = "GB18030|GB2312|GBK|CP936";

    public static final String CONTENT_TYPE_REGEX_FORMAT = "(?i)contentType=\".{0,200};?charset=(%s)\"";
    public static final String GB_CONTENT_TYPE_REGEX = String.format(CONTENT_TYPE_REGEX_FORMAT, GB_CHARSET_STRING);
    public static final Pattern GB_CONTENT_TYPE_PATTERN = Pattern.compile(GB_CONTENT_TYPE_REGEX);

    public static final String PAGE_ENCODING_REGEX_FORMAT = "(?i)pageEncoding=\"?(%s)\"?";
    public static final String GB_PAGE_ENCODING_REGEX = String.format(PAGE_ENCODING_REGEX_FORMAT, GB_CHARSET_STRING);
    public static final Pattern GB_PAGE_ENCODING_PATTERN = Pattern.compile(GB_PAGE_ENCODING_REGEX);

    public static final String CONTENT_REGEX_FORMAT = "(?i)content=\".{0,200};?charset=(%s)\"";
    public static final String GB_CONTENT_REGEX = String.format(CONTENT_REGEX_FORMAT, GB_CHARSET_STRING);
    public static final Pattern GB_CONTENT_PATTERN = Pattern.compile(GB_CONTENT_REGEX);

    public static final String META_CHARSET_REGEX_FORMAT = "(?i)meta.{1,200}charset=\"(%s)\"";
    public static final String GB_META_CHARSET_REGEX = String.format(META_CHARSET_REGEX_FORMAT, GB_CHARSET_STRING);
    public static final Pattern GB_META_CHARSET_PATTERN = Pattern.compile(GB_META_CHARSET_REGEX);

    public static final String ENCODING_REGEX_FORMAT = "(?i)encoding=\"?(%s)\"?";
    public static final String GB_ENCODING_REGEX = String.format(ENCODING_REGEX_FORMAT, GB_CHARSET_STRING);
    public static final Pattern GB_ENCODING_PATTERN = Pattern.compile(GB_ENCODING_REGEX);

    public static final List<String> DEFAULT_SELECTED_FILE_TYPES = List.of("java");
    public static final List<String> CONTENT_REPLACE_FILE_TYPES = List.of(FILE_TYPE_JSP, FILE_TYPE_XML, FILE_TYPE_HTML);
    public static final String MESSAGE_TITLE = "File-Encoding-Converter";

    public static final String I18N_SELECT_FILE_TYPES_KEY = "SELECT_FILE_TYPES_KEY";
    public static final String I18N_SELECT_TARGET_CHARSET_KEY = "SELECT_TARGET_CHARSET_KEY";
    public static final String I18N_REPLACE_CHARSET_KEY = "REPLACE_CHARSET_KEY";
}
