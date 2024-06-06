package com.tony.fast.architecture.model.system;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdateOwnPasswordReq {
    @ApiModelProperty(value = "旧密码", example = "123456", required = true)
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @ApiModelProperty(value = "新密码", example = "123456", required = true)
    @NotBlank(message = "新密码不能为空")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z~!@#$%^&*()_?]{12,20}$", message = "密码格式不正确，要求12-20位，至少包含1个数字、1个字母")
    private String newPassword;
    @ApiModelProperty(value = "确认新密码", example = "123456", required = true)
    @NotBlank(message = "确认新密码不能为空")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z~!@#$%^&*()_?]{12,20}$", message = "密码格式不正确，要求12-20位，至少包含1个数字、1个字母")
    private String reNewPassword;
}
