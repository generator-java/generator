package com.gen.util;

/**
 * @author fanhb on 2018/9/22
 * @desc xxxx
 */
public class StringFormatUtil {

    public static String format = "%-30s";

    public static String getFormat30(String str) {
        return String.format(format, str);
    }

    public static String getSymbolFormat30(String str) {
        return String.format(format, "#{"+str+"}");
    }

    /**
     *
     * @return 返回
     */
    public static String getQuotaFormat30(String str) {
        return String.format(format, "\""+str+"\"");
    }
}
