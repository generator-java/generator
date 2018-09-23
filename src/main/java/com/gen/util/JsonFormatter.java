package com.gen.util;


/**
 * @date 2013-3-10
 *
 * @author fanhaibo (2018年06月10日) and whose name which i don't know
 * @date 2018年06月10日12:11:34
 * @version 1.0.0 snapshot
 *
 */
public class JsonFormatter {
    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
    public static void printFormattingJson(String jsonStr) {
        //json 字符串
        int level = 0;
        //存放格式化的json字符串
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int index=0;index<jsonStr.length();index++)//将字符串中的字符逐个按行输出
        {
            //获取s中的每个字符
            char c = jsonStr.charAt(index);
//          System.out.println(s.charAt(index));
             
            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
//                System.out.println("123"+jsonForMatStr);
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
            case '{':
            case '[':
                jsonForMatStr.append(c + "\n");
                level++;
                break;
            case ',':
                jsonForMatStr.append(c + "\n");            
                break;
            case '}':
            case ']':
                jsonForMatStr.append("\n");
                level--;
                jsonForMatStr.append(getLevelStr(level));
                jsonForMatStr.append(c);
                break;
            default:
                jsonForMatStr.append(c);
                break;
            }
        }
        System.out.println(jsonForMatStr);
 
    }
     
}