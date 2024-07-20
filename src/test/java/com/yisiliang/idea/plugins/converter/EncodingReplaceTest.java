package com.yisiliang.idea.plugins.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodingReplaceTest {
    public static void main(String[] args) {
        String jspCode = "<%@page language=\"java\" contentType=\"text/html;charset=utf-8\" pageEncoding=\"utf-8\"%>\n" +
                "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">\n" +
                "    <title>一个 JSP 测试页面</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    一个 JSP 测试页面。<br>\n" +
                "    <% out.print(\"测试 JSP 编码\");%>\n" +
                "</body>\n" +
                "</html>";

        String replacedCode = replaceSpecificEncoding(jspCode);
        System.out.println(replacedCode);
    }

    public static String replaceSpecificEncoding(String input) {

        //pageEncoding="GB18030",pageEncoding="GBK",pageEncoding="2312"
        // 定义正则表达式模式，只匹配 pageEncoding 和 contentType 后面的 utf-8
        String pattern = "(?<=pageEncoding\\=\")utf-8(?=\")|(?<=contentType\\=\")utf-8(?=\")";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 创建 Matcher 对象
        Matcher m = r.matcher(input);
        return m.replaceAll("GB18030");
    }
}
