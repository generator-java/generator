package com.gen.genFile;

import java.lang.Integer;
import java.lang.Integer;
import java.lang.String;


/**
* @author zhanSan
* @email zhanSan@gmail.com>
* @date 2018-09-23
* @desc java文件头的备注信息
* @version 0.0.1
*/
public class KeyvalueJobEntity {

    /** 键值对表, 或者叫记不住,问题表, 服务表,为知笔记,日记. whatever */
    private Integer id;
    /** 非原理名称 */
    private String keyName;
    /** 问题 */
    private String question;
    /** 相关人 */
    private String relatedPerson;
    /** 原理内容 */
    private String principleContent;
    /** 语言.文学.数学.音乐等 */
    private String language;
    /** 原理名称 */
    private String axomName;
    /** 指定日期 */
    private String calendar;
    /** 网址_不存放二进制文件
 */
    private String webSite;
    /** 大内容存储 */
    private String largeContent;
    /** 文件存放路径  /User/van/Desktop/test */
    private String path;
    /** axis 维度 */
    private String dimension;
    /** 记录人 */
    private String author;
    /** 地点.公司等(时间地点人物) */
    private String situs;
    /** 状态：0-无效；1-有效；2-冻结 */
    private Integer status;
    /** 更新时间 */
    private String updateTime;
    /** 附言 */
    private String postscript;
    /** 创建时间 */
    private String createTime;


    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }

    public void setKeyName(String keyName){
        this.keyName = keyName;
    }

    public String getKeyName(){
        return this.keyName;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public String getQuestion(){
        return this.question;
    }

    public void setRelatedPerson(String relatedPerson){
        this.relatedPerson = relatedPerson;
    }

    public String getRelatedPerson(){
        return this.relatedPerson;
    }

    public void setPrincipleContent(String principleContent){
        this.principleContent = principleContent;
    }

    public String getPrincipleContent(){
        return this.principleContent;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public String getLanguage(){
        return this.language;
    }

    public void setAxomName(String axomName){
        this.axomName = axomName;
    }

    public String getAxomName(){
        return this.axomName;
    }

    public void setCalendar(String calendar){
        this.calendar = calendar;
    }

    public String getCalendar(){
        return this.calendar;
    }

    public void setWebSite(String webSite){
        this.webSite = webSite;
    }

    public String getWebSite(){
        return this.webSite;
    }

    public void setLargeContent(String largeContent){
        this.largeContent = largeContent;
    }

    public String getLargeContent(){
        return this.largeContent;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }

    public void setDimension(String dimension){
        this.dimension = dimension;
    }

    public String getDimension(){
        return this.dimension;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setSitus(String situs){
        this.situs = situs;
    }

    public String getSitus(){
        return this.situs;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return this.status;
    }

    public void setUpdateTime(String updateTime){
        this.updateTime = updateTime;
    }

    public String getUpdateTime(){
        return this.updateTime;
    }

    public void setPostscript(String postscript){
        this.postscript = postscript;
    }

    public String getPostscript(){
        return this.postscript;
    }

    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }

    public String getCreateTime(){
        return this.createTime;
    }


}