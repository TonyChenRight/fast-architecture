package com.tony.fast.architecture.domain;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tony.fast.architecture.model.OperationLogContext;
import com.tony.fast.architecture.model.UserInfo;
import lombok.Data;

/**
 * 操作日志
 * @TableName operation_log
 */
@TableName(value ="operation_log")
@Data
public class OperationLog implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作模块
     */
    @TableField(value = "module")
    private String module;

    /**
     * 操作类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 操作对象ID
     */
    @TableField(value = "target_id")
    private String targetId;

    /**
     * IP
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 参数
     */
    @TableField(value = "params")
    private Object params;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

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

    public static List<OperationLog> buildByCreate(OperationLogContext context, UserInfo userInfo, String ip, String params) {
        List<OperationLog> result =new ArrayList<>();

        long timeMillis = System.currentTimeMillis();
        for (String targetId : context.getTargetIds()) {
            OperationLog operationLog=new OperationLog();
            operationLog.setModule(context.getModule().name());
            operationLog.setType(context.getType().name());
            operationLog.setTargetId(targetId);
            operationLog.setIp(ip);
            operationLog.setParams(params);
            operationLog.setRemark(context.getRemark());
            operationLog.setCreatedAt(timeMillis);
            String code = userInfo.getCode();
            String name = userInfo.getName();
            if (StrUtil.isBlank(code)) {
                code = "SYS";
                name = "系统";
            }
            operationLog.setCreatorCode(code);
            operationLog.setCreatorName(name);
            result.add(operationLog);
        }

        return result;
    }
}