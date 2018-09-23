package com.gen.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Date;


/**
 * 问题表实体
 *
 * @version <pre>
 * Author	Version		Date		Changes
 * van 	1.0  2018年08月18日 Created
 *
 * </pre>
 * @since 1.
 */
public class ErrBugTogetherEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /*键值对表, 或者叫记不住,问题表, 服务表,为知笔记,日记. whatever*/
    private Integer id;
    /*非原理名称*/
//    @NotEmpty(message = "关键字不能空,搜索时使用的关键字")
    private String keyName;
    /*问题*/
    private String question;
    /*1:正常,2:bug,3:其他*/
    private String type;
    /*相关人*/
    private String relatedPerson;
    /*原理内容*/
    private String principleContent;
    /*语言.文学.数学.音乐等*/
    private String language;
    /*原理名称*/
    private String axomName;
    /*指定日期*/
    private Date calendar;
    /*网址_不存放二进制文件 */
    private String webSite;
    /*大内容存储*/
    private String largeContent;
    /*文件存放路径  /User/van/Desktop/test*/
    private String path;
    /*axis 维度*/
    private String dimension;
    /*记录人*/
    private String author;
    /*地点.公司等(时间地点人物)*/
    private String situs;
    /*状态：0-无效；1-有效；2-冻结*/
    private Integer status;
    /*更新时间*/
    private Date updateTime;
    /*附言*/
    private String postscript;
    /*创建时间*/
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelatedPerson() {
        return relatedPerson;
    }

    public void setRelatedPerson(String relatedPerson) {
        this.relatedPerson = relatedPerson;
    }

    public String getPrincipleContent() {
        return principleContent;
    }

    public void setPrincipleContent(String principleContent) {
        this.principleContent = principleContent;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAxomName() {
        return axomName;
    }

    public void setAxomName(String axomName) {
        this.axomName = axomName;
    }

    public Date getCalendar() {
        return calendar;
    }

    public void setCalendar(Date calendar) {
        this.calendar = calendar;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getLargeContent() {
        return largeContent;
    }

    public void setLargeContent(String largeContent) {
        this.largeContent = largeContent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSitus() {
        return situs;
    }

    public void setSitus(String situs) {
        this.situs = situs;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        try {

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(this);
//            String formatJson = JsonFormatUtil.formatJson(json);
//            System.out.println(formatJson);


            return json;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("failed to json " + this.getClass().getName());
        }
    }
}