package cn.dhbin.isme.common.config;
// File: FieldNamesConfig.java

/**
 * 定义用于自动填充的字段名称的配置接口。
 * 其他模块可以通过实现这个接口或者直接使用这些常量来获取字段名。
 */
public interface FieldNamesConfig {
    // 创建时间的字段名
    String CREATE_TIME = "createTime";
    
    // 更新时间的字段名
    String UPDATE_TIME = "updateTime";
    
    // 创建者的字段名
    String CREATE_BY = "createUser";
    
    // 更新者的字段名
    String UPDATE_BY = "updateUser";
}