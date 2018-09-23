package com.gen.hello.test;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelloVelocityTest {
    public static void main(String[] args) {

        System.out.println(System.getProperty("user.dir"));
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate("vm/hellovelocity.vm");
        VelocityContext ctx = new VelocityContext();

        ctx.put("name", "velocity");
        ctx.put("comments", "11111");
        ctx.put("author", "fanhaibo_900");
        ctx.put("datetime", (new Date()).toLocaleString());
        ctx.put("className", "MyVMTest");
        ctx.put("comments", "11111");
        ctx.put("iAmVariable", "iAmVariable");
        ctx.put("iAmVariable", "iAmVariable");
        ctx.put("date", (new Date()).toString());

        List temp = new ArrayList();
        temp.add("1");
        temp.add("2");
        ctx.put("list", temp);
        StringWriter sw = new StringWriter();

        t.merge(ctx, sw);

        System.out.println(sw.toString());
    }
}