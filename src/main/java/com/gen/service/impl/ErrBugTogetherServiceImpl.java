package com.gen.service.impl;

import com.gen.mapper.ErrBugTogetherMapper;
import com.gen.model.ErrBugTogetherEntity;
import com.gen.model.TableInfo;
import com.gen.service.ErrBugTogetherService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fanhb on 2018/8/18
 * @desc xxxx
 */

@Service
public class ErrBugTogetherServiceImpl implements ErrBugTogetherService {


    @Autowired
    private ErrBugTogetherMapper errBugTogetherMapper;

    @Override
    public ErrBugTogetherEntity getErrBugTogether(int id) {
        ErrBugTogetherEntity errBugTogether = errBugTogetherMapper.getErrBugTogetherById(id);
        return errBugTogether;
    }
    @Override
    public List<String> findByKeyName(String keyName) {
        List<String> keyNameList = errBugTogetherMapper.findByType(keyName);
        return keyNameList;
    }

    @Override
    public List<TableInfo > getColumnInfo(String tabName, String dbName) {
        return errBugTogetherMapper.getColumnInfo(tabName,dbName);
    }

    @Override
    @Transactional
    public int updateErrBugById(ErrBugTogetherEntity entity) {
        return errBugTogetherMapper.updateErrBugTogetherById(entity);
    }

    @Override
    @Transactional
    public int insertErrBugTogether(ErrBugTogetherEntity entity) {
        return errBugTogetherMapper.insertErrBugTogether(entity);
    }

    @Override
    public PageInfo<ErrBugTogetherEntity> findErrBug( String find,String type) {
        List<ErrBugTogetherEntity> list  = errBugTogetherMapper.findErrBug(find,type);
        PageInfo<ErrBugTogetherEntity> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    @Transactional
    public void deleteErrBugById(int id) {
        errBugTogetherMapper.deleteErrBugTogether(id);
    }
}
