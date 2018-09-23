package com.gen.service;

import com.gen.model.ErrBugTogetherEntity;
import com.gen.model.TableInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author fanhb on 2018/8/18
 * @desc xxxx
 */


public interface ErrBugTogetherService {


    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    ErrBugTogetherEntity getErrBugTogether(int id);

    /**
     * 新增内容
     *
     * @param entity
     * @return
     */
    int insertErrBugTogether(ErrBugTogetherEntity entity);

    /**
     * 修改errBugTogether
     *
     * @param entity
     * @return
     */
    int updateErrBugById(ErrBugTogetherEntity entity);

    /**
     * 搜索keyName,largeContent
     *
     * @param type the type of errBug
     * @param find like '#{find}'
     * @return
     */
    PageInfo<ErrBugTogetherEntity> findErrBug(String find, String type);

    void deleteErrBugById(int id);

    List<String> findByKeyName(String keyName);

    List<TableInfo> getColumnInfo(String tabName, String dbName);
}
