package com.yisiliang.idea.plugins.converter;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.CharsetToolkit;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertUtils {
    private static final Logger LOGGER = Logger.getInstance(ConvertUtils.class);

    public static byte[] convert(String fileType, Charset source, Charset target, byte[] sourceBytes, boolean changeCharsetInFileContent) {
        int bomLen = CharsetToolkit.getBOMLength(sourceBytes, source);
        CharBuffer charBuffer = source.decode(ByteBuffer.wrap(sourceBytes, bomLen, sourceBytes.length - bomLen));
        ByteBuffer byteBuffer = target.encode(charBuffer);
        byte[] convertBytes = byteBuffer.array();
        byte[] tmpBytes = new byte[byteBuffer.limit() - byteBuffer.position()];
        System.arraycopy(convertBytes, byteBuffer.position(), tmpBytes, 0, byteBuffer.limit() - byteBuffer.position());
        LOGGER.debug("convert " + source + ":" + Arrays.toString(sourceBytes) + " to " + target + ":" + Arrays.toString(tmpBytes));
        if (changeCharsetInFileContent && ConverterConstants.CONTENT_REPLACE_FILE_TYPES.contains(fileType)) {
            String converted = new String(tmpBytes, target);
            converted = replaceCharsetInContent(fileType, converted, source, target);
            tmpBytes = converted.getBytes(target);
        }
        return tmpBytes;
    }

    /**
     * jsp: 1, 2, 3, 4
     * html: 1, 3, 4
     * xml: 5
     * <br />
     * 1. contentType="text/html;charset=utf-8"
     * <br />
     * 2. pageEncoding="utf-8"
     * <br />
     * 3. content="text/html;charset=utf-8"
     * <br />
     * 4. meta charset="utf-8"
     * <br />
     * 5. encoding="GB18030"
     *
     * @param fileType  文件类型
     * @param converted 文件内容
     * @param source    源编码
     * @param target    目标编码
     * @return 编码被替换后内容
     */
    private static String replaceCharsetInContent(String fileType, String converted, Charset source, Charset target) {
        if (converted == null || converted.isEmpty() || converted.isBlank()) {
            return converted;
        }
        if (ConverterConstants.FILE_TYPE_JSP.equalsIgnoreCase(fileType)) {
            converted = replaceCharsetInJsp(converted, source, target);
        }
        if (ConverterConstants.FILE_TYPE_HTML.equalsIgnoreCase(fileType)) {
            converted = replaceCharsetInHtml(converted, source, target);
        }
        if (ConverterConstants.FILE_TYPE_XML.equalsIgnoreCase(fileType)) {
            converted = replaceCharsetInXml(converted, source, target);
        }
        return converted;
    }

    protected static String replaceCharsetInJsp(String jspString, Charset source, Charset target) {
        jspString = replaceCharsetInContentType(jspString, source, target);
        jspString = replaceCharsetInPageEncoding(jspString, source, target);
        jspString = replaceCharsetInContent(jspString, source, target);
        jspString = replaceCharsetInMetaCharset(jspString, source, target);
        return jspString;
    }

    protected static String replaceCharsetInXml(String xmlString, Charset source, Charset target) {
        return replaceCharsetInEncoding(xmlString, source, target);
    }

    protected static String replaceCharsetInHtml(String jspString, Charset source, Charset target) {
        jspString = replaceCharsetInContentType(jspString, source, target);
        jspString = replaceCharsetInContent(jspString, source, target);
        jspString = replaceCharsetInMetaCharset(jspString, source, target);
        return jspString;
    }

    private static String replaceCharsetInContentType(String xmlString, Charset source, Charset target) {
        return replaceCharset(xmlString, source, target, ConverterConstants.GB_CONTENT_TYPE_PATTERN, ConverterConstants.CONTENT_TYPE_REGEX_FORMAT);
    }

    private static String replaceCharsetInPageEncoding(String xmlString, Charset source, Charset target) {
        return replaceCharset(xmlString, source, target, ConverterConstants.GB_PAGE_ENCODING_PATTERN, ConverterConstants.PAGE_ENCODING_REGEX_FORMAT);
    }

    private static String replaceCharsetInContent(String xmlString, Charset source, Charset target) {
        return replaceCharset(xmlString, source, target, ConverterConstants.GB_CONTENT_PATTERN, ConverterConstants.CONTENT_REGEX_FORMAT);
    }

    private static String replaceCharsetInMetaCharset(String xmlString, Charset source, Charset target) {
        return replaceCharset(xmlString, source, target, ConverterConstants.GB_META_CHARSET_PATTERN, ConverterConstants.META_CHARSET_REGEX_FORMAT);
    }

    private static String replaceCharsetInEncoding(String xmlString, Charset source, Charset target) {
        return replaceCharset(xmlString, source, target, ConverterConstants.GB_ENCODING_PATTERN, ConverterConstants.ENCODING_REGEX_FORMAT);
    }

    private static String replaceCharset(String xmlString, Charset source, Charset target, Pattern gbPattern, String regexFormat) {
        String sourceCharset = source.toString();
        String targetCharset = target.toString();
        Matcher matcher;
        if (ConverterConstants.GB_CHARSET_LIST.contains(source)) {
            matcher = gbPattern.matcher(xmlString);
        } else {
            matcher = Pattern.compile(String.format(regexFormat, sourceCharset)).matcher(xmlString);
        }
        StringBuilder result = new StringBuilder();
        boolean matched = false;
        int lastEnd = 0;
        while (matcher.find()) {
            matched = true;
            int start = matcher.start(1);
            result.append(xmlString, lastEnd, start).append(targetCharset);
            lastEnd = matcher.end(1);
        }
        result.append(xmlString.substring(lastEnd));
        if (matched) {
            return result.toString();
        } else {
            return xmlString;
        }
    }

}
