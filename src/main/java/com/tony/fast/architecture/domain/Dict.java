package com.tony.fast.architecture.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 字典表
 * @TableName dict
 */
@TableName(value ="dict")
@Data
public class Dict implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类型
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 父级编码
     */
    @TableField(value = "parent_code")
    private String parentCode;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 权重
     */
    @TableField(value = "weight")
    private Integer weight;

    /**
     * 状态  0:禁用  1:正常
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Long createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at")
    private Long updatedAt;

    /**
     * 创建人编码
     */
    @TableField(value = "creator_code")
    private String creatorCode;

    /**
     * 更新人编码
     */
    @TableField(value = "updater_code")
    private String updaterCode;

    /**
     * 创建人名称
     */
    @TableField(value = "creator_name")
    private String creatorName;

    /**
     * 更新人名称
     */
    @TableField(value = "updater_name")
    private String updaterName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}