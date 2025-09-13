package cn.dhbin.isme.common.mybatis;

import cn.dev33.satoken.stp.StpUtil;
import cn.dhbin.isme.common.auth.SaTokenConfigure;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static cn.dhbin.isme.common.config.FieldNamesConfig.*;

/**
 * MyBatis-Plus 自动填充处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, CREATE_BY, this::getCurrentUserId, String.class);
        this.strictInsertFill(metaObject, UPDATE_BY, this::getCurrentUserId, String.class);
    }

    /**
     * 更新填充字段的方法
     *
     * @param metaObject 元对象，包含对象的属性和值
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, UPDATE_BY, this::getCurrentUserId, String.class);
    }

    /**
     * 获取当前用户ID（需要根据您的认证系统实现）
     */
    private String getCurrentUserId() {
        // 这里需要根据您的认证系统获取当前用户ID
        // 例如使用SaToken: return StpUtil.getLoginIdAsString();
        return (String) StpUtil.getExtra(SaTokenConfigure.JWT_USERNAME_KEY); // 默认值
    }
}