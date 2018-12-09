package com.gu.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class BiMapDemo {

   public static void main(String args[]) {
      BiMap<Integer, String> empIDNameMap = HashBiMap.create();

      empIDNameMap.put(new Integer(101), "Mahesh");
      empIDNameMap.put(new Integer(102), "Sohan");
      empIDNameMap.put(new Integer(103), "Ramesh");

      //Emp Id of Employee "Mahesh"
      BiMap<String, Integer> inverse = empIDNameMap.inverse();
      System.out.println(inverse.get("Mahesh"));
   }	
}