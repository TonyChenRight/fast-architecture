package com.tony.fast.architecture.domain;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.tony.fast.architecture.enums.StatusType;
import com.tony.fast.architecture.model.UserInfo;
import com.tony.fast.architecture.model.user.UserEditReq;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 编码,用户账号
     */
    @TableField(value = "code")
    private String code;

    /**
     * 用户姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 登录密码
     */
    @TableField(value = "password")
    private String password;

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

    public static User buildByCreate(UserEditReq req, String genPassword, UserInfo opUser) {
        User user =new User();
        BeanUtil.copyProperties(req, user);
        long timeMillis = System.currentTimeMillis();
        user.setPassword(genPassword);
        user.setStatus(StatusType.ENABLE.getVal());
        user.setCreatorCode(opUser.getCode());
        user.setCreatorName(opUser.getName());
        user.setCreatedAt(timeMillis);
        user.setUpdaterCode(opUser.getCode());
        user.setUpdaterName(opUser.getName());
        user.setUpdatedAt(timeMillis);
        return user;
    }

    public static User buildByUpdate(UserEditReq req, UserInfo opUser) {
        User user =new User();
        req.setCode(null);
        BeanUtil.copyProperties(req, user);
        user.setUpdaterCode(opUser.getCode());
        user.setUpdaterName(opUser.getName());
        user.setUpdatedAt(System.currentTimeMillis());
        return user;
    }
}