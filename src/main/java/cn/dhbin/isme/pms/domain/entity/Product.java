package cn.dhbin.isme.pms.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 产品表（用于测试代码生成）
 * </p>
 *
 * @author your_name
 * @since 2025-09-06
 */
@Getter
@Setter
@TableName("product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品价格（精确到分）
     */
    private BigDecimal price;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 是否上架（0-下架，1-上架）
     */
    private Boolean isOnSale;

    /**
     * 分类ID（关联分类表）
     */
    private Long categoryId;

    /**
     * 产品重量（kg）
     */
    private Double weight;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新用户
     */
    private LocalDateTime updateUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 产品详细描述
     */
    private String description;

    /**
     * 封面图片URL
     */
    private String coverImage;

    /**
     * 排序权重（值越大越靠前）
     */
    private Byte sort;
}
