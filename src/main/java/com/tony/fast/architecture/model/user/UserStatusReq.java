package com.tony.fast.architecture.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserStatusReq {
    @ApiModelProperty(value = "用户ID", example = "1", required = true)
    @NotNull(message = "ID不能为空")
    private Long id;

    @ApiModelProperty(value = "状态 0-注销 1-正常", example = "1", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;
}
