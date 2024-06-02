package com.tony.fast.architecture.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户角色表
 * @TableName role_permission
 */
@TableName(value ="role_permission")
@Data
public class RolePermission implements Serializable {
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色编码
     */
    @TableField(value = "role_code")
    private String roleCode;

    /**
     * 权限编码
     */
    @TableField(value = "permission_code")
    private String permissionCode;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Long createdAt;

    /**
     * 创建人编码
     */
    @TableField(value = "creator_code")
    private String creatorCode;

    /**
     * 创建人名称
     */
    @TableField(value = "creator_name")
    private String creatorName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}