package com.gen.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.config.ConfigData;
import com.gen.model.*;
import com.gen.type.TypeMapping;
import com.gen.util.FieldUtils;
import com.gen.util.StringFormatUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author fanhaibo (2018年06月10日) and hongten (2013-3-10)
 * @version 1.0.0 snapshot
 * @date 2013-3-10
 * @date 2018年06月10日12:11:34
 */

public class GeneratorJavaFileUtils {
    Logger logger = LoggerFactory.getLogger(GeneratorJavaFileUtils.class);


    public static String PROJECT_PATH = ConfigData.PROJECT_PATH.getValue();// /target/classes/
    public static String PROJECT_TEST_PATH = ConfigData.PROJECT_TEST_PATH.getValue();//SpringBoot

    private static FileInfo fileInfo = new FileInfo();
    private List<TableInfo> dataList;
    private Set<TypeMapping> importSet = new HashSet<>();
    private String tabName;
    private static DeveloperInfo developerInfo = new DeveloperInfo();
    public static String tableInfoJson;

    public GeneratorJavaFileUtils(List<TableInfo> dataList, String tabName) {


        int length = 0;
        for (TableInfo tableInfo : dataList) {
            int curLength = tableInfo.getColumnName().length();
            if (curLength >= length) {
                length = curLength + 2;
            }
        }
        if (length == 0) {
            length = 30;
        }
        StringFormatUtil.format = "%-" + length + "s";


        this.dataList = dataList;
        this.tabName = tabName;
    }

    /**
     * 创建bean的Service的实现<br>
     *
     * @throws Exception
     */

    public void createBeanFiles(String entityName, GeneratedFileInfo gfi) {
        initVMParameters(entityName, gfi);

        doCreateFiles(entityName, gfi);
    }

    /**
     * 主要处理xml文件中的格式和逗号,#号
     */
    private void initVMParameters(String entityName, GeneratedFileInfo gfi) {
        fileInfo.setLowerName(FieldUtils.triggerFirstLetterLower(entityName));
        developerInfo.setAuthorName(ConfigData.AUTHOR_NAME.getValue());
        developerInfo.setAuthorMail(ConfigData.AUTHOR_MAIL.getValue());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConfigData.DATE_FORMAT.getValue());
        developerInfo.setDate(simpleDateFormat.format(new Date()));
        developerInfo.setVersion(ConfigData.VERSION.getValue());
        fileInfo.setName(entityName);
        fileInfo.setTabName(tabName);

        //处理路径, 把文件放到指定位置
        String absPath = PathInfo.getFileAbsPath(gfi.getJavaFilePath(), gfi.getJavaSuffix());
        String packagePath = PathInfo.toJavaPackage(absPath);
        fileInfo.setBeanDaoUrl(packagePath);


        for (TableInfo tableInfo : dataList) {
            tableInfo.setTabName(tabName);
            importSet.add(tableInfo.getJavaType());
        }


        /** 处理最后一个逗号 */
        TableInfo tableInfo = dataList.get(dataList.size() - 1);
        tableInfo.setColNameHumpQuotaComma30(tableInfo.getColNameHumpQuota30());
        tableInfo.setColNameSymbolHumpComma30(tableInfo.getColNameSymbolHump30());
        tableInfo.setColumnNameComma30(tableInfo.getColumnName30());
        tableInfo.setColumnNameQuotaComma30(tableInfo.getColumnNameQuota30());

        //获取实体类的全路径, eg: com.gen.model.DeveloperInfo, 作为xml的resultType
        String pkgPath = PathInfo.packagePath + gfi.getJavaFilePath() + gfi.getEntityName();
        pkgPath = pkgPath.replaceAll("/", ".");
        gfi.setPkgPathEntityName(pkgPath);

    }

    private void doCreateFiles(String entityName, GeneratedFileInfo gfi) {
        //
        try {
            String absPath = PathInfo.getFileAbsPath(gfi.getJavaFilePath(), gfi.getJavaSuffix());

            File filePath = new File(absPath);
            //创建目录
            createFilePath(filePath);
            //创建文件
            String fileName = absPath + entityName + gfi.getJavaSuffix();
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file);

            String GeneratedCodeFile = doPutDataIntoVelocityEngineCreateFiles(gfi, fileInfo, developerInfo);
            fw.write(GeneratedCodeFile);
            fw.flush();
            fw.close();
            showInfo(fileName);

        } catch (Exception e) {
            throw new RuntimeException("failed to create java files", e);
        }
    }


    /**
     * 根据模板生成代码
     *
     * @param gfi           模板信息
     * @param fileInfo      目标bean
     * @param developerInfo 注释
     * @return
     * @throws Exception
     */
    private String doPutDataIntoVelocityEngineCreateFiles(GeneratedFileInfo gfi, FileInfo fileInfo, DeveloperInfo developerInfo) throws Exception {

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("input.encoding", "UTF-8");
        velocityEngine.setProperty("output.encoding", "UTF-8");
        if (PROJECT_PATH.equals(ConfigData.DEF_PROJECT_PATH.getValue()) || "MAVEN".equalsIgnoreCase(ConfigData.PROJECT_NAME.getValue())) {
            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        }
        velocityEngine.init();
        Template template = velocityEngine.getTemplate(gfi.getVmName());
        VelocityContext velocityContext = new VelocityContext();
        DataToVelocity.setDeveloperInfo(developerInfo);
        DataToVelocity.setFileInfo(fileInfo);
        DataToVelocity.setGfi(gfi);
        DataToVelocity.setImportSet(importSet);

        Map<String, Object> allDataToNgx = DataToVelocity.getAllDataToNgx();
        for (Map.Entry<String, Object> entry : allDataToNgx.entrySet()) {
            velocityContext.put(entry.getKey(), entry.getValue());

        }

        //生成文件需要的主要数据
        velocityContext.put("dataList", dataList);

        tableInfoJson = new ObjectMapper().writeValueAsString(dataList);
        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);
        return stringWriter.toString();

    }

    /**
     * 创建文件
     *
     * @param file
     */
    private void createFilePath(File file) {
        if (!file.exists()) {
            logger.info("创建文件夹[" + file.getAbsolutePath() + "]情况：" + file.mkdirs());
        } else {
            logger.info("存在文件夹：" + file.getAbsolutePath());
        }
    }


    /**
     * 显示信息
     *
     * @param info
     */
    private void showInfo(String info) {
        logger.info("创建文件：" + info + "成功！");
        System.out.println("创建文件：" + info + "成功！");
    }


}

