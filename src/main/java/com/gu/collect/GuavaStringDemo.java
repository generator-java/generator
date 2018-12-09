package com.gu.collect;

import java.util.Arrays;

import com.google.common.base.*;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;


public class GuavaStringDemo {
   public static void main(String args[]) {
      testJoiner();
      testSplitter();
      testCharMatcher();
      testCaseFormat();
   }

   private static void testJoiner() {
      System.out.println(Joiner.on(",")
         .skipNulls()
         .join(Arrays.asList(1,2,3,4,5,null,6)));
   }

   private static void testSplitter() {
      System.out.println(Splitter.on(',')
              .trimResults()
              .omitEmptyStrings()
              .split("the ,quick, ,brown, fox, jumps, over, the, lazy, little dog."));
   }


   private static void testCharMatcher() {
      System.out.println(CharMatcher.inRange('a','z').retainFrom("mahesh123"));    // only the digits
      System.out.println(CharMatcher.whitespace().trimAndCollapseFrom("     Mahesh     Parashar ", ' '));

      // trim whitespace at ends, and replace/collapse whitespace into single spaces
      System.out.println(CharMatcher.javaDigit().replaceFrom("mahesh123", "*"));  // star out all digits
      System.out.println(CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom("maheSH123"));

      // eliminate all characters that aren't digits or lowercase
   }

   private static void testCaseFormat() {
      String data = "test_data";//HYPHEN 连字符， underscore 下划线，
      System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));
      System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));
      System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));
   }
}