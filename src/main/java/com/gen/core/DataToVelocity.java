package com.gen.core;

import com.gen.util.JsonMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fanhb on 2018/9/22
 * @desc 放入velocity engine中的属性, 供模板使用
 */
public class DataToVelocity extends HashMap<String,Object> {

    private static final Map<String,Object> dataInNgx = new ConcurrentHashMap<>();
    
    private static String fileInfo = "fileInfo";
    private static String developerInfo = "developerInfo";
    private static String gfi = "gfi";
    private static String importSet = "importSet";

    public static void setFileInfo(Object fileInfo) {
        dataInNgx.put(DataToVelocity.fileInfo,fileInfo);
    }

    public static void setDeveloperInfo(Object developerInfo) {
        dataInNgx.put(DataToVelocity.developerInfo,developerInfo);
    }

    public static void setGfi(Object gfi) {
        dataInNgx.put(DataToVelocity.gfi,gfi);
    }

    public static void setImportSet(Object importSet) {
        dataInNgx.put(DataToVelocity.importSet,importSet);
    }

    @Override
    public Object put(String key,Object val) {
        if (dataInNgx.containsKey(key)){
            throw new RuntimeException(String.format("{%s}已经被使用", key));
        }
        return dataInNgx.putIfAbsent(key,val);
    }
    public static Map<String,Object> getAllDataToNgx() {
        DataToVelocity data = new DataToVelocity();
        data.putAll(dataInNgx);
        return data;
    }

    @Override
    public String toString() {
        return JsonMapper.getInstance().toJson(dataInNgx);
    }
}
