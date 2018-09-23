package com.gen.model;


import com.gen.type.TypeMapping;
import com.gen.util.FieldUtils;
import com.gen.util.StringFormatUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stefan
 * @version 0.0.1
 * @email my_email@gmail.com>
 * @date 2018-09-21
 */
public class TableInfo {

    public static String INT = "INT";
    public static String INTEGER = "INTEGER";
    public static String TEXT = "TEXT";
    public static String VARCHAR = "VARCHAR";
    public static String DATETIME = "DATETIME";
    public static String TIMESTAMP = "TIMESTAMP";
    private final static Map<String,String> map = new HashMap<>();
    {
        map.put(INT,INTEGER);
        map.put(TEXT,VARCHAR);
        map.put(DATETIME,TIMESTAMP);
    }
    //字段名称
    private String columnName;
    private String columnName30;//定长
    private String columnNameComma30;//逗号,定长
    private String columnNameQuota30;//引号,定长
    private String columnNameQuotaComma30;//引号,逗号,定长
    private String gsetter; //驼峰且首字母大写
    private String colNameHump;//驼峰
    private String colNameHumpQuota30;//驼峰,引号,定长
    private String colNameHumpQuotaComma30;//驼峰,引号,逗号,定长
    private String colNameSymbolHumpComma30;//#号,驼峰,逗号,定长
    private String colNameSymbolHump30;//#号,驼峰,定长

    //字段注释
    private String columnComment;
    //数据类型,JDBC TYPE
    private String dataType;
    private TypeMapping javaType;
    private String dataTypeQuota30;
    private String tabName;



    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getDataType() {
        String keyUpper = dataType.toUpperCase();
        if (map.containsKey(keyUpper)){
            this.dataType = map.get(keyUpper);
            return ObjectUtils.toString(map.get(dataType)).toUpperCase();
        }
        return ObjectUtils.toString(dataType).toUpperCase();
    }

    public String getColNameHump() {

        return FieldUtils.lineToHump(columnName);
    }

    public void setColNameHump(String colNameHump) {
        this.colNameHump = colNameHump;
    }

    public String getGsetter() {
        String gSetter = FieldUtils.lineToHumpGSetter(columnName);
        return gSetter;
    }

    public void setGsetter(String gsetter) {
        this.gsetter = gsetter;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    public String getColumnName30() {
        return StringFormatUtil.getFormat30(columnName);
    }

    public void setColumnName30(String columnName30) {
        this.columnName30 = columnName30;
    }

    public String getColumnNameQuota30() {
        return StringFormatUtil.getQuotaFormat30(columnName);
    }

    public void setColumnNameQuota30(String columnNameQuota30) {
        this.columnNameQuota30 = columnNameQuota30;
    }

    public String getColumnNameQuotaComma30() {
        return StringFormatUtil.getQuotaFormat30(columnName) + ",";
    }

    public void setColumnNameQuotaComma30(String columnNameQuotaComma30) {
        this.columnNameQuotaComma30 = columnNameQuotaComma30;
    }

    public String getColNameHumpQuota30() {
        String hump = FieldUtils.lineToHump(columnName);
        return StringFormatUtil.getQuotaFormat30(hump);
    }

    public void setColNameHumpQuota30(String colNameHumpQuota30) {
        this.colNameHumpQuota30 = colNameHumpQuota30;
    }

    public String getColNameHumpQuotaComma30() {
        if (StringUtils.isNotBlank(colNameHumpQuotaComma30)) {
            return colNameHumpQuotaComma30;
        }
        String hump = FieldUtils.lineToHump(columnName);
        return StringFormatUtil.getQuotaFormat30(hump) + ",";
    }

    public void setColNameHumpQuotaComma30(String colNameHumpQuotaComma30) {
        this.colNameHumpQuotaComma30 = colNameHumpQuotaComma30;
    }

    public String getColNameSymbolHumpComma30() {

        if (StringUtils.isNotBlank(colNameSymbolHumpComma30)) {
            return colNameSymbolHumpComma30;
        }
        String hump = FieldUtils.lineToHump(columnName);
        return StringFormatUtil.getSymbolFormat30(hump) + ",";
    }

    public void setColNameSymbolHumpComma30(String colNameSymbolHumpComma30) {
        this.colNameSymbolHumpComma30 = colNameSymbolHumpComma30;
    }

    public String getColumnNameComma30() {
        if (StringUtils.isNotBlank(columnNameComma30)) {
            return columnNameComma30;
        }
        return StringFormatUtil.getFormat30(columnName) + ",";
    }

    public void setColumnNameComma30(String columnNameComma30) {
        this.columnNameComma30 = columnNameComma30;
    }

    public String getDataTypeQuota30() {
        /** 如果是IBATIS不支持的类型, 通过map转换一下;  可能类型不全 */
        String upperCase = ObjectUtils.toString(dataType).toUpperCase();
        String keyUpper = dataType.toUpperCase();

        if (map.containsKey(keyUpper)){
            dataType = map.get(keyUpper);
            upperCase = ObjectUtils.toString(map.get(dataType)).toUpperCase();
        }
        return StringFormatUtil.getQuotaFormat30(upperCase);
    }

    public void setDataTypeQuota30(String dataTypeQuota30) {
        this.dataTypeQuota30 = dataTypeQuota30;
    }
    public String getColNameSymbolHump30() {
        if (StringUtils.isNotBlank(colNameSymbolHump30)) {
            return colNameSymbolHump30;
        }
        String hump = FieldUtils.lineToHump(columnName);
        return StringFormatUtil.getSymbolFormat30(hump);
    }

    public void setColNameSymbolHump30(String colNameSymbolHump30) {
        this.colNameSymbolHump30 = colNameSymbolHump30;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public TypeMapping getJavaType() {
        TypeMapping javaType = TypeMapping.getJavaType(dataType);
        return javaType;
    }


//INT
//TEXT
//DATETIME

}