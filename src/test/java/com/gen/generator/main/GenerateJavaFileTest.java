package com.gen.generator.main;

import com.gen.config.ConfigData;
import com.gen.core.DataToVelocity;
import com.gen.core.GeneratorJavaFileUtils;
import com.gen.core.InitGeneratedFileInfo;
import com.gen.model.TableInfo;
import com.gen.service.ErrBugTogetherService;
import com.gen.util.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * @author fanhb on 2018/9/21
 * @desc xxxx
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateJavaFileTest {
    @Autowired
    private ErrBugTogetherService errBugTogetherService;

    /** 运行代码, 直接生成文件于本项目/test/java/com.gen.generator.genFile下 */
    @Test
    public void generateJavaFileEtc() {

        String desc = "java文件头的备注信息";
        String dbName = "axiom"; // 数据库名称
        String tabName = "keyvalue_job"; //表名
        ConfigData.setAuthorName("zhanSan");
        ConfigData.setAuthorMail("zhanSan@gmail.com");

        /** 放入allDataToNgx,可以直接在模板引擎中使用 */
//        Map<String, Object> allDataToNgx = DataToVelocity.getAllDataToNgx();

        String keyValueJob = FieldUtils.lineToHumpGSetter(tabName); //实体类名称
        List<TableInfo> columnInfo = errBugTogetherService.getColumnInfo(tabName, dbName);

        GeneratorJavaFileUtils generatorJavaFileUtils = new GeneratorJavaFileUtils(columnInfo,tabName);
        InitGeneratedFileInfo.initGeneratedFileInfo(generatorJavaFileUtils, keyValueJob,desc);
        /** 填充到velocity中的数据的json形式 */
        System.err.println(GeneratorJavaFileUtils.tableInfoJson);
        boolean empty = ObjectUtils.isEmpty(columnInfo);
        assert (false == empty);

    }

}
