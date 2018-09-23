package com.gen.test;

public class TestSystemProperty {

    public static void main(String[] args) {

        System.out.println(System.getProperty("user.dir"));

        System.out.println(TestSystemProperty.class.getPackage().getName());
    }
}
