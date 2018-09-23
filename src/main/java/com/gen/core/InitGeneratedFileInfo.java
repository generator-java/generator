package com.gen.core;

import com.gen.model.GeneratedFileInfo;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanhaibo (2018年06月10日) and hongten (2013-3-10)
 * @version 1.0.0 snapshot
 * @date 2013-3-10
 * @date 2018年06月10日12:11:34
 */
public class InitGeneratedFileInfo {

    public static final GeneratedFileInfo DAO = new GeneratedFileInfo();
    public static final GeneratedFileInfo DAO_IMPL = new GeneratedFileInfo();
    public static final GeneratedFileInfo SERVICE = new GeneratedFileInfo();
    public static final GeneratedFileInfo SERVICE_IMPL = new GeneratedFileInfo();
    public static final GeneratedFileInfo MODEL = new GeneratedFileInfo();
    public static final GeneratedFileInfo XML = new GeneratedFileInfo();


    private static final List<GeneratedFileInfo> fileInfoList = new ArrayList<>();

    static {

        //生成dao代码
        DAO.setJavaSuffix("Dao.java");
        DAO.setVmName("vm/beanDao.vm");
        //生成dao代码
        DAO_IMPL.setJavaSuffix("DaoImpl.java");
        DAO_IMPL.setVmName("vm/beanDaoImpl.vm");

        SERVICE.setJavaSuffix("Service.java");
        SERVICE.setVmName("vm/beanService.vm");

        SERVICE_IMPL.setJavaSuffix("ServiceImpl.java");
        SERVICE_IMPL.setVmName("vm/beanServiceImpl.vm");

        MODEL.setJavaSuffix("Entity.java");
        MODEL.setVmName("vm/beanEntity.vm");

        XML.setJavaSuffix("-Mapper.xml");
        XML.setVmName("vm/bean.xml.vm");


        fileInfoList.add(DAO);
        fileInfoList.add(DAO_IMPL);
        fileInfoList.add(SERVICE);
        fileInfoList.add(SERVICE_IMPL);
        fileInfoList.add(MODEL);
        fileInfoList.add(XML);
    }

    public static void initGeneratedFileInfo(GeneratorJavaFileUtils generatorJavaFileUtils, String targetEntity,String desc) {

        String[] split = StringUtils.split(MODEL.getJavaSuffix(), ".");
        String entityName = targetEntity + split[0];
        for (GeneratedFileInfo gfi : fileInfoList) {
            gfi.setDesc(desc);
            gfi.setJavaFilePath("/genFile/");
            gfi.setEntityName(entityName);
            generatorJavaFileUtils.createBeanFiles(targetEntity, gfi);
        }
    }


}







