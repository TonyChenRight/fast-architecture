package com.tony.fast.architecture.model.system;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VerifyImgReq {
    @ApiModelProperty(value = "宽度", example = "111")
    private Integer width;
    @ApiModelProperty(value = "高度", example = "111")
    private Integer height;
}
