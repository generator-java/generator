package com.gen.util;

import java.io.*;

/**
 * @author fanhb on 2018/9/22
 * @desc xxxx
 */
public class CopyUtils {

    public static <T> T deepCopy(T obj) {
        try {
            ByteArrayOutputStream bio = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bio);

            oos.writeObject(obj);
            ByteArrayInputStream bis = new ByteArrayInputStream(bio.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(String.format("failed to deep copy %s", obj.getClass().getName()));
        }
    }
}
