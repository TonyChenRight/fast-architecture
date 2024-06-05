package com.tony.fast.architecture.model.user;

import com.tony.fast.architecture.model.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserListReq extends BasePage {
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long id;
    @ApiModelProperty(value = "编码", example = "xxx@xx.com")
    private String code;
    @ApiModelProperty(value = "用户名", example = "tony")
    private String username;
    @ApiModelProperty(value = "状态 0-禁用 1-正常", example = "1")
    private Integer status;
}
