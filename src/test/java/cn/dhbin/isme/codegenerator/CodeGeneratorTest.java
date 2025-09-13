package cn.dhbin.isme.codegenerator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.IOutputFile;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

import static cn.dhbin.isme.common.config.FieldNamesConfig.*;

/**
 * 放在test目录中的代码生成器，通过JUnit测试方法执行
 */
@SpringBootTest() // 读取test环境配置
public class CodeGeneratorTest {

    // 从test环境的配置文件中读取数据库信息
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    // 基础路径配
    String parentPackage = "cn.dhbin.isme";

    String templateDir = "/templates/";
    // 代码输出目录（指向main目录，确保生成的代码被项目识别）
    private String outputDir = System.getProperty("user.dir") + "/src/main/java";

    // Mapper XML输出目录
    private String mapperXmlDir = System.getProperty("user.dir") + "/src/main/resources/mapper/pms";


    private String author = "Dylan Du";
    @Test
    public void generateCode() {
        FastAutoGenerator.create(dbUrl, dbUsername, dbPassword)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author(author)
                            .outputDir(outputDir)
                            .disableOpenDir();
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent(parentPackage)
                            .moduleName("pms")
                            .entity("domain.entity")
                            .mapper("mapper")
                            .service("service")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(
                                    OutputFile.xml,  // XML文件用OutputFile.mapperXml
                                    mapperXmlDir           // XML文件的存放路径（如src/main/resources/mapper）
                            ));
                })
                // 策略配置（生成product表）
                .strategyConfig(builder -> {
                    // 添加自动填充字段
                    List<IFill> tableFills = new ArrayList<>();
                        tableFills.add(new Property(CREATE_TIME, FieldFill.INSERT));
                        tableFills.add(new Property(UPDATE_TIME, FieldFill.INSERT_UPDATE));
                        tableFills.add(new Property(CREATE_BY, FieldFill.INSERT));
                        tableFills.add(new Property(UPDATE_BY, FieldFill.INSERT_UPDATE));
                    builder.entityBuilder().addTableFills(tableFills);

                    builder.addInclude("product")
                            .addTablePrefix("")
                            .serviceBuilder().disableService()
                            .entityBuilder().enableLombok()
                            .controllerBuilder().enableRestStyle();
                })
                .injectionConfig(builder -> {
                    // 自定义REQUEST文件生成
                    String requestPath = outputDir + "/cn/dhbin/isme/pms/domain/request";
                    // 自定义文件
                   CustomFile customFile = new CustomFile.Builder()
                           .filePath(requestPath)
                           .templatePath("/templates/request.java.ftl")
                           .fileName("Request.java")
                           .build();
                    builder.customFile(customFile);

                    // 自定义前端api.js 文件的生成
                    String apiPath = outputDir + "/vue";
                    CustomFile customApiJsFile = new CustomFile.Builder()
                            .filePath(apiPath)
                            .templatePath("/templates/api.js.ftl")
                            .formatNameFunction(tableInfo -> "")
                            .fileName("api.js")
                            .build();
                    builder.customFile(customApiJsFile);

                    // 自定义前端index.vue 文件的生成
                    String vuePath = outputDir + "/vue";
                    CustomFile customVuePathFile = new CustomFile.Builder()
                            .filePath(vuePath)
                            .templatePath("/templates/index.vue.ftl")
                            .formatNameFunction(tableInfo -> "")
                            .fileName("index.vue")
                            .build();
                    builder.customFile(customVuePathFile);



                    Map<String, Object> customMap = new HashMap<>();
                    customMap.put("requestpackage", "cn.dhbin.isme.pms.domain.request");
                    customMap.put("RPackage", "cn.dhbin.isme.common.response");
                    builder.customMap(customMap);

                    builder.beforeOutputFile((tableInfo, objectMap) -> {
                        TableField f = tableInfo.getFields().stream().filter(fieldInfo -> fieldInfo.isKeyIdentityFlag()).findFirst().get();
                        objectMap.put("customServiceName", tableInfo.getServiceImplName().replace("impl", ""));
                        objectMap.put("primaryKeyJavaType", f.getPropertyType());
                        objectMap.put("primaryKeyFieldName", f.getPropertyName());
                        String formattedFileName = tableInfo.getEntityName() + "Request";
                        objectMap.put("requestFileName", formattedFileName);
                        objectMap.put("requestPackage", "cn.dhbin.isme.pms.domain.request");

                        // 设置系统字段配置
                        Map<String, Object> config = new HashMap<>();
                        config.put("systemFields", Arrays.asList(
                            "id",
                            CREATE_TIME,
                            UPDATE_TIME,
                            CREATE_BY,
                            UPDATE_BY
                        ));
                        objectMap.put("config", config);
                    });
                })
                // 模板配置（使用test目录中的自定义模板）
                .templateConfig(builder -> {
                    builder.entity("/templates/entity.java.ftl")
                           .service(null)
                            .serviceImpl("/templates/serviceImpl.java.ftl")
                            .controller("/templates/controller.java.ftl");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
