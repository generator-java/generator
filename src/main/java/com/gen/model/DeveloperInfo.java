package com.gen.model;


/**
 * @date 2013-3-10
 *
 * @author fanhaibo (2018年06月10日) and hongten (2013-3-10)
 * @date 2018年06月10日12:11:34
 * @version 1.0.0 snapshot
 *
 */
public final class DeveloperInfo {

    /**
     * 作者名称
     */
    private String authorName;
    /**
     * 作者邮箱
     */
    private String authorMail;
    /**
     * 日期
     */
    private String date;
    /**
     * 版本
     */
    private String version;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorMail() {
        return authorMail;
    }

    public void setAuthorMail(String authorMail) {
        this.authorMail = authorMail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}