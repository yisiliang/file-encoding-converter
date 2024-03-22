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
                byte[] convert = ConvertUtils.convert(charset, GB18030, bytes);
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
                byte[] convert = ConvertUtils.convert(charset, StandardCharsets.UTF_8, bytes);
                System.out.println(Arrays.toString(convert));
                String utf8String = new String(convert, StandardCharsets.UTF_8);
                System.out.println(utf8String);
                Assert.assertTrue(utf8String.contains("System.out.println(\"我是中国人\");"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}