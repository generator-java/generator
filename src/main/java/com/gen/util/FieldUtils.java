package com.gen.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldUtils {
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern firstPattern = Pattern.compile("[a-zA-Z]");

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        StringBuffer sb = new StringBuffer();

        lineToHump(str, sb);
        return sb.toString();
    }

    private static void lineToHump(String str, StringBuffer sb) {
        Matcher matcher = linePattern.matcher(str);
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
    }

    /** 首字母大写的驼峰 */
    public static String lineToHumpGSetter(String str) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        Matcher m = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(str);
        while (m.find()) {
            m.appendReplacement(sb2, m.group(1).toUpperCase() + m.group(2).toLowerCase());
        }
        lineToHump(sb2.toString(), sb);
        return sb.toString();
    }
    /** 首字母大写的驼峰 */
    public static String triggerFirstLetterLower(String str) {
        StringBuffer sbuf = new StringBuffer();
        Matcher m = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(str);
        while (m.find()) {
            m.appendReplacement(sbuf, m.group(1).toLowerCase() + m.group(2).toLowerCase());
        }
        return sbuf.toString();
    }

    /** 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)}) */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线,效率比上面高
     */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        String lineToHump = lineToHumpGSetter("name");
        String lineToHump2 = lineToHumpGSetter("F_PARENT_NO_LEADER");
        System.out.println(lineToHump);//fParentNoLeader
        System.out.println(lineToHump2);//fParentNoLeader
//        System.out.println(humpToLine(lineToHump));//f_parent_no_leader
//        System.out.println(humpToLine2(lineToHump));//f_parent_no_leader
    }
}

