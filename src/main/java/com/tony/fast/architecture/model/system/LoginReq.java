package com.tony.fast.architecture.model.system;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginReq {
    @ApiModelProperty(value = "账号", example = "user", required = true)
    @NotBlank(message = "账号不能为空")
    private String username;
    @ApiModelProperty(value = "密码", example = "123456", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;
    @ApiModelProperty(value = "验证码ID", example = "p9yY0gMcpTsB3hewdZS7K", required = true)
    @NotBlank(message = "验证码ID不能为空")
    private String captchaId;
    @ApiModelProperty(value = "验证码", example = "123456", required = true)
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
}
