package com.yisiliang.idea.plugins.converter;

import com.intellij.util.ResourceUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ConvertUtilsTest {
    private static final Charset GB18030 = Charset.forName("GB18030");

    @Test
    public void convertToGB() {
        try {
            String[] filenames = new String[]{"GB18030.txt", "UTF8.txt", "UTF16BE.txt", "UTF16LE.txt"};
            BytesEncodingDetect bytesEncodingDetect = new BytesEncodingDetect();
            for (String filename : filenames) {
                byte[] bytes = ResourceUtil.getResourceAsBytes("toG/" + filename, this.getClass().getClassLoader());
                System.out.println(filename + " bytes ->" + Arrays.toString(bytes));
                Charset charset = bytesEncodingDetect.detectEncoding(bytes);
                System.out.println(filename + " charset ->" + charset);
                byte[] convert = ConvertUtils.convert("txt", charset, GB18030, bytes, true);
                System.out.println(Arrays.toString(convert));
                String gbString = new String(convert, GB18030);
                System.out.println(gbString);
                Assert.assertTrue(gbString.contains("System.out.println(\"我是中国人\");"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void convertToU8() {
        try {
            String[] filenames = new String[]{"GB18030.txt", "UTF8.txt", "UTF16BE.txt", "UTF16LE.txt"};
            BytesEncodingDetect bytesEncodingDetect = new BytesEncodingDetect();
            for (String filename : filenames) {
                byte[] bytes = ResourceUtil.getResourceAsBytes("toG/" + filename, this.getClass().getClassLoader());
                System.out.println(filename + " bytes ->" + Arrays.toString(bytes));
                Charset charset = bytesEncodingDetect.detectEncoding(bytes);
                System.out.println(filename + " charset ->" + charset);
                byte[] convert = ConvertUtils.convert("txt", charset, StandardCharsets.UTF_8, bytes, false);
                System.out.println(Arrays.toString(convert));
                String utf8String = new String(convert, StandardCharsets.UTF_8);
                System.out.println(utf8String);
                Assert.assertTrue(utf8String.contains("System.out.println(\"我是中国人\");"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void replaceXml() {
        {
            String xml = "<?xml version=\"1.0\" encoding=\"GB18030\"?>\n<a></a>";
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_CP936, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GB2312, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GBK, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GB18030, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
        }
        {
            String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n<a></a>";
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_CP936, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GB2312, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GBK, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GB18030, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
        }
        {
            String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>\n<a></a>";
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_CP936, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GB2312, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GBK, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GB18030, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
        }
        {
            String xml = "<?xml version=\"1.0\" encoding=\"cp936\"?>\n<a></a>";
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_CP936, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GB2312, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GBK, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
            {
                String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_GB18030, StandardCharsets.UTF_8);
                System.out.println(replaceCharsetInXml);
                Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
            }
        }
    }


    @Test
    public void xml2() {
        String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?><?xml version=\"1.0\" encoding=\"gb2312\"?>\n<a></a>";
        {
            String replaceCharsetInXml = ConvertUtils.replaceCharsetInXml(xml, ConverterConstants.CHARSET_CP936, StandardCharsets.UTF_8);
            System.out.println(replaceCharsetInXml);
            Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>", replaceCharsetInXml);
        }
    }
}