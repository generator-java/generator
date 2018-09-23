package com.gen.config;




/**
 * @author fanhaibo (2018年06月10日)
 * @date 2018年06月10日12:11:34
 * @version 1.0.0 snapshot
 */
public class ConfigData {

    /**
     * @code /Users/van/wiser/generator/${DEF_PROJECT_PATH}/com/gen/config/dao/impl/UserDaoImpl.java 等效于下面这个路径
     * @eg /Users/van/wiser/generator/src/main/java/com/gen/config/dao/impl/UserDaoImpl.java
     * @desc 该demo适用于maven结构的项目, 非maven项目需要修改这个路径参数
     * @eg : SpringBoot
     *
     * /src/main/test/ 放在测试路径下也应该修该参数
     */
    public static ConfigData DEF_PROJECT_PATH = new ConfigData("/src/main/java/", "MAVEN 非测试类的项目绝对路径参数");
    public static ConfigData DEF_PROJECT_TEST_PATH= new ConfigData("/src/test/java/", "MAVEN 测试类的项目绝对路径参数");
    public static ConfigData AUTHOR_NAME= new ConfigData("stefan", "作者");
    public static ConfigData AUTHOR_MAIL= new ConfigData("my_email@gmail.com", "邮箱");
    public static ConfigData VERSION= new ConfigData("0.0.1", "版本号");
    public static ConfigData DATE_FORMAT= new ConfigData("yyyy-MM-dd", "日期格式化样式");
    // 非springBoot可能需要修改这些参数, 主要是根据class文件位置来获取放置文件的绝对路径
    public static ConfigData PROJECT_PATH= new ConfigData("/target/classes/", "SpringBoot非测试代码中生成代码会用到的参数, 其他项目可能需要改这个参数");
    public static ConfigData PROJECT_TEST_PATH= new ConfigData("/target/test-classes/", "SpringBoot测试代码中生成代码会用到的参数, 其他项目可能需要改这个参数");
    public static ConfigData PROJECT_NAME= new ConfigData("MAVEN", "MAVEN 项目");

    public String value;
    public String desc;
    public static String authorName;
    public static String authorMail;




    ConfigData(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String getAuthorName() {
        return authorName;
    }

    public static void setAuthorName(String authorName) {
        AUTHOR_NAME = new ConfigData(authorName,"new author name");
    }

    public static String getAuthorMail() {
        return authorMail;
    }

    public static void setAuthorMail(String authorMail) {
        AUTHOR_MAIL = new ConfigData(authorMail,"new author email");

    }
}