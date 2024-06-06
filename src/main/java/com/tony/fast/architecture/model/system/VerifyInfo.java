package com.tony.fast.architecture.model.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyInfo {
    @ApiModelProperty(value = "验证码ID", example = "fsdfsfsfsf")
    private String id;
    @ApiModelProperty(value = "验证码图片base64", example = "fsdfsffdsfsfsfdsf")
    private String img;
}
