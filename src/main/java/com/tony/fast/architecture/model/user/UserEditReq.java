package com.tony.fast.architecture.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserEditReq {
    @ApiModelProperty(value = "用户ID，修改必填", example = "1")
    private Long id;

    @ApiModelProperty(value = "编码,登录账号", example = "admin", required = true)
    @NotBlank(message = "编码不能为空")
    private String code;

    @ApiModelProperty(value = "用户名", example = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String name;
}
