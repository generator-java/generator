# velocity代码生成器
```
1: 基于velocity,SprongBoot的代码生成器 , 连接mysql, 通过数据库表生成实体类和注释,xml文件等
2: 易拓展
3: 后期添加各种java代码最佳实践代码生成
```
# 使用特别简单,拓展简单,只用了velocity和java, 非常易与拓展
```
主要是要java处理数据格式,velocity用了替换模板中的代码, 对熟悉java的朋友拓展特别友好
```
第一步: 修改数据库连接,连接用户,和密码 
```
    --/resource/application-dev.xml
    --url: jdbc:mysql://127.0.0.1:3306/axiom?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false
```

第二步: 修改数据库名称,表名,java文件头的作者,邮箱
```
    修改参数位置: /test/java/com.gen.generator.main.GenerateJavaFileTest
    --String dbName = "axiom"; // 数据库名称
    --String tabName = "keyvalue_job"; //表名
    --ConfigData.setAuthorName("zhanSan");
    --ConfigData.setAuthorMail("zhanSan@gmail.com");
```  

第三步: 用junit运行代码直接生成代码
```
    --被生成代码的位置: /test/java/com.gen.generator.genFile
```

第四步: db2拓展
```
   修改/resource/mapper/table-info-Mapper.xml/getColumnInfo中的sql为db2的表结构查询语句,
   并且修改返回的实体类
   select * from sysibm.columns where tabschema = 'XXUSER' and tabname = 'PRODUCT' 
```

```
有共享和贡献代码的道友欢迎来电:183 2872 0393
```
