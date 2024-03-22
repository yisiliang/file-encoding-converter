package com.yisiliang.idea.plugins.converter;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.CharsetToolkit;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class ConvertUtils {
    private static final Logger LOGGER = Logger.getInstance(ConvertUtils.class);

    public static byte[] convert(Charset source, Charset target, byte[] sourceBytes) {
        int bomLen = CharsetToolkit.getBOMLength(sourceBytes, source);
        CharBuffer charBuffer = source.decode(ByteBuffer.wrap(sourceBytes, bomLen, sourceBytes.length - bomLen));
        ByteBuffer byteBuffer = target.encode(charBuffer);
        byte[] convertBytes = byteBuffer.array();
        byte[] tmpBytes = new byte[byteBuffer.limit() - byteBuffer.position()];
        System.arraycopy(convertBytes, byteBuffer.position(), tmpBytes, 0, byteBuffer.limit() - byteBuffer.position());
        LOGGER.debug("convert " + source + ":" + Arrays.toString(sourceBytes) + " to " + target + ":" + Arrays.toString(tmpBytes));
        return tmpBytes;
    }
}
