package com.yisiliang.idea.plugins.converter;

import com.intellij.util.ResourceUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BytesEncodingDetectTest {
    @Test
    public void guessCharsetGB18030() {
        try {
            String filename = "test-GB18030.txt";
            byte[] bytes = ResourceUtil.getResourceAsBytes(filename, this.getClass().getClassLoader());
            BytesEncodingDetect bytesEncodingDetect = new BytesEncodingDetect();
            Charset charset = bytesEncodingDetect.detectEncoding(bytes);
            System.out.println(filename + "->" + charset);
            Assert.assertEquals(charset, EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.GB18030]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void guessCharsetUTF8() {
        try {
            String filename = "test-UTF-8.txt";
            byte[] bytes = ResourceUtil.getResourceAsBytes(filename, this.getClass().getClassLoader());
            BytesEncodingDetect bytesEncodingDetect = new BytesEncodingDetect();
            Charset charset = bytesEncodingDetect.detectEncoding(bytes);
            System.out.println(filename + "->" + charset);
            Assert.assertEquals(charset, EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.UTF8]);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void guessCharsetUTF16BE() {
        try {
            String filename = "test-UTF16BE.txt";
            byte[] bytes = ResourceUtil.getResourceAsBytes(filename, this.getClass().getClassLoader());
            BytesEncodingDetect bytesEncodingDetect = new BytesEncodingDetect();
            Charset charset = bytesEncodingDetect.detectEncoding(bytes);
            System.out.println(filename + "->" + charset);
            Assert.assertEquals(charset, EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.UTF16BE]);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void guessCharsetUTF16LE() {
        try {
            String filename = "test-UTF16LE.txt";
            byte[] bytes = ResourceUtil.getResourceAsBytes(filename, this.getClass().getClassLoader());
            BytesEncodingDetect bytesEncodingDetect = new BytesEncodingDetect();
            Charset charset = bytesEncodingDetect.detectEncoding(bytes);
            System.out.println(filename + "->" + charset);
            Assert.assertEquals(charset, EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.UTF16LE]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void guessCharset() {
        try {
            String[] filenames = new String[] {"GB18030.txt", "UTF8.txt", "UTF16BE.txt", "UTF16LE.txt", "UTF32BE.txt", "UTF32LE.txt"};
            BytesEncodingDetect bytesEncodingDetect = new BytesEncodingDetect();
            for (String filename : filenames) {
                byte[] bytes = ResourceUtil.getResourceAsBytes("toG/" + filename, this.getClass().getClassLoader());
                System.out.println(Arrays.toString(bytes));
                Charset charset = bytesEncodingDetect.detectEncoding(bytes);
                System.out.println(filename + "->" + charset);
                if (filename.equals("GB18030.txt")) {
                    Assert.assertEquals(charset, EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.GB18030]);
                }
                if (filename.equals("UTF8.txt")) {
                    Assert.assertEquals(charset, EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.UTF8]);
                }
                if (filename.equals("UTF16BE.txt")) {
                    Assert.assertEquals(charset, EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.UTF16BE]);
                }
                if (filename.equals("UTF16LE.txt")) {
                    Assert.assertEquals(charset, EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.UTF16LE]);
                }

                if (filename.equals("UTF32BE.txt")) {
                    Assert.assertEquals(charset, EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.UTF32BE]);
                }
                if (filename.equals("UTF32LE.txt")) {
                    Assert.assertEquals(charset, EncodingConstants.SUPPORT_CHARSET_ARRAY[EncodingConstants.UTF32LE]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void convert() {
        String string = "我是中国人";
        {
            Charset detectEncoding = Charset.forName("GB18030");
            Charset targetEncoding = StandardCharsets.UTF_8;
            byte[] oriBytes = string.getBytes(detectEncoding);
            System.out.println(oriBytes.length);
            CharBuffer charBuffer = detectEncoding.decode(ByteBuffer.wrap(oriBytes));
            ByteBuffer byteBuffer = targetEncoding.encode(charBuffer);
            byte[] convertBytes = byteBuffer.array();
            String target = new String(convertBytes, byteBuffer.position(), byteBuffer.limit(), targetEncoding);
            System.out.println(byteBuffer.limit());
            System.out.println(target);
        }
        {
            Charset detectEncoding = StandardCharsets.UTF_8;
            Charset targetEncoding = Charset.forName("GB18030");
            byte[] oriBytes = string.getBytes(detectEncoding);
            System.out.println(oriBytes.length);
            CharBuffer charBuffer = detectEncoding.decode(ByteBuffer.wrap(oriBytes));
            ByteBuffer byteBuffer = targetEncoding.encode(charBuffer);
            byte[] convertBytes = byteBuffer.array();
            String target = new String(convertBytes, byteBuffer.position(), byteBuffer.limit(), targetEncoding);
            System.out.println(byteBuffer.limit());
            System.out.println(target);
        }
    }
}