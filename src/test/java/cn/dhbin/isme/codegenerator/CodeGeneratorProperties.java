package cn.dhbin.isme.codegenerator;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "generator")
public class CodeGeneratorProperties {
    // 数据库配置
    private DbConfig db = new DbConfig();
    // 全局配置
    private GlobalConfig global = new GlobalConfig();
    // 包配置
    private PackageConfig packageConfig = new PackageConfig();
    // 策略配置
    private StrategyConfig strategy = new StrategyConfig();
    // 模板配置
    private TemplateConfig template = new TemplateConfig();

    // 内部类：数据库配置
    @Data
    public static class DbConfig {
        private String url;
        private String username;
        private String password;
    }

    // 内部类：全局配置
    @Data
    public static class GlobalConfig {
        private String author;
        private String outputDir; // 代码输出根目录
        private boolean openDir; // 是否打开输出目录
    }

    // 内部类：包配置
    @Data
    public static class PackageConfig {
        private String parent; // 父包名
        private String moduleName; // 模块名
        private String entity; // 实体类包名
        private String mapper; // Mapper接口包名
        private String service; // Service接口包名
        private String serviceImpl; // Service实现类包名
        private String controller; // Controller包名
        private String mapperXml; // Mapper XML路径
    }

    // 内部类：策略配置
    @Data
    public static class StrategyConfig {
        private List<String> include; // 需要生成的表名
        private List<String> tablePrefix; // 表前缀过滤
        private boolean enableLombok; // 是否启用Lombok
        private boolean restControllerStyle; // 是否生成REST风格控制器
    }

    // 内部类：模板配置
    @Data
    public static class TemplateConfig {
        private String entity; // 实体类模板路径
        private String mapper; // Mapper接口模板路径
        private String service; // Service接口模板路径
        private String serviceImpl; // Service实现类模板路径
        private String controller; // Controller模板路径
    }
}