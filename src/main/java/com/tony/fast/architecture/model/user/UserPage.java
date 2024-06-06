package com.tony.fast.architecture.model.user;

import cn.hutool.core.bean.BeanUtil;
import com.tony.fast.architecture.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserPage {
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long id;
    @ApiModelProperty(value = "编码", example = "cccc")
    private String code;
    @ApiModelProperty(value = "名称", example = "xxx")
    private String name;
    @ApiModelProperty(value = "密码", example = "cccc")
    private String password;
    @ApiModelProperty(value = "状态 0-禁用 1-启用", example = "1")
    private Integer status;
    @ApiModelProperty(value = "创建时间", example = "2023-12-07 10:10:10")
    private Date createdAt;
    @ApiModelProperty(value = "创建时间", example = "2023-12-07 10:10:10")
    private Date updatedAt;
    @ApiModelProperty(value = "创建人编码", example = "1")
    private String creatorCode;
    @ApiModelProperty(value = "更新人编码", example = "1")
    private String updaterCode;
    @ApiModelProperty(value = "创建人名称", example = "张三")
    private String creatorName;
    @ApiModelProperty(value = "更新人名称", example = "张三")
    private String updaterName;

    public UserPage(User user) {
        BeanUtil.copyProperties(user, this);
    }
}
