package com.gen.model;

/**
 * GeneratedFileInfo: javaFile, velocity, path
 *
 * @author fanhaibo
 * @version 1.0.0 snapshot
 * @date 2018年06月10日12:11:34
 */

public final class GeneratedFileInfo {


    private String JavaSuffix;//dao/service interface suffix
    private String javaFilePath;//dao/service interface's path
    private String vmName; //dao/service velocity file name
    private String desc; //remark
    /** 实体类名称 */
    private String entityName;
    /** 实体全路径加名称 */
    private String pkgPathEntityName;

    /**
     * @return //Dao.java
     */
    public String getJavaSuffix() {
        return JavaSuffix;
    }

    /**
     * @return /dao/
     */
    public String getJavaFilePath() {//
        return javaFilePath;
    }

    /**
     * @return //baseDao.vm
     */
    public String getVmName() {
        return vmName;
    }

    public String getDesc() {
        return desc;
    }

    public void setJavaSuffix(String javaSuffix) {
        this.JavaSuffix = javaSuffix;
    }

    public void setJavaFilePath(String javaFilePath) {
        this.javaFilePath = javaFilePath;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getPkgPathEntityName() {
        return pkgPathEntityName;
    }

    public void setPkgPathEntityName(String pkgPathEntityName) {
        this.pkgPathEntityName = pkgPathEntityName;
    }
}