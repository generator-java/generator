package com.gen.hello.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeFirstLetterUppercaseTest {
    public static void main(String[] args) {
        String str = "this is a java test String upcase: 123string";
        System.out.println(str);
        StringBuffer stringbf = new StringBuffer();
        Matcher m = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(str);

        while (m.find()) {
            m.appendReplacement(stringbf, m.group(1).toUpperCase() + m.group(2).toLowerCase());
        }
        System.out.println(m.appendTail(stringbf).toString());
    }
}