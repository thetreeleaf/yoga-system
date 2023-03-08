package com.yoga.common;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeneratorApp {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        String moduleName = scanner("模块名");
        String tableName = scanner("表名(多表用逗号，隔开；或前缀匹配，如xxx*)");
        /*代码生成器*/
        AutoGenerator mpg = new AutoGenerator();
        /* 全局配置 */
        GlobalConfig gc = new GlobalConfig();
        //当前项目路径
        String projectPath = System.getProperty("user.dir");
        //设置生成路径
        gc.setOutputDir(projectPath + "/yoga-course/src/main/java");
        //生成作者
        gc.setAuthor("Jisoo");
        //代码生成后是否打开所在文件夹
        gc.setOpen(false);
        //生成Swagger2注解
        gc.setSwagger2(true);
        //是否在xml生成属性字段映射
        gc.setBaseResultMap(true);
        //同文件生成覆盖
        gc.setFileOverride(true);
        //设置时间格式类型
        gc.setDateType(DateType.ONLY_DATE);
        //实体类命名方法 %s表示直接用表名
        gc.setEntityName("%s");
        //mapper接口命名方法 %sMapper
        gc.setMapperName("%sMapper");
        //xml命名
        gc.setXmlName("%sMapper");
        //service接口命名
        gc.setServiceName("%sService");
        //serviceImpl实现类命名
        gc.setServiceImplName("%sServiceImpl");
        //全局配置对象设置进代码生成器中
        mpg.setGlobalConfig(gc);
        /*数据源配置*/
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/yoga?serverTimezone=UTC");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root123");
        mpg.setDataSource(dsc);

        /*包配置*/
        PackageConfig pc = new PackageConfig();
        //模块名
        pc.setModuleName(moduleName);
        //包名
        pc.setParent("com.yoga");
        //完整的包名 = cn.zhku.generator.user
        mpg.setPackageInfo(pc);

        /*自定义配置*/
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/yoga-course/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        //输出配置放入自定义配置
        cfg.setFileOutConfigList(focList);
        //自定义配置放入生成器
        mpg.setCfg(cfg);
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //把已有的xml置空
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        /*策略配置*/
        StrategyConfig strategy = new StrategyConfig();
        //表名生成策略：下划线转驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //列名生成策略：下划线转驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //设置pojo的父类
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        //实体是否支持Lombok
//        strategy.setEntityLombokModel(true);
        //是否是RestController
        strategy.setRestControllerStyle(true);
        /*公共父类*/
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        /*写于父类中的公共字段*/
//        strategy.setSuperEntityColumns("id");
        if (tableName.indexOf('*') > 0) {
            //模糊指定要生成的表名
            strategy.setLikeTable(new LikeTable(tableName.replace("*", "_")));
        } else {
            //要生成的表名
            strategy.setInclude(tableName.split(","));
        }
        //RequestMapping的驼峰转连字符
//        strategy.setControllerMappingHyphenStyle(true);
        //表名替换前缀
//        strategy.setTablePrefix(prefixName);
        mpg.setStrategy(strategy);

        /*开始生成*/
        mpg.execute();
    }

}
