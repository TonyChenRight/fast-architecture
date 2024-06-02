package com.tony.fast.architecture.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;
    @ApiModelProperty(value = "登录账号", example = "xxxx")
    private String account;
    @ApiModelProperty(value = "用户名", example = "xxxx")
    private String name;
}