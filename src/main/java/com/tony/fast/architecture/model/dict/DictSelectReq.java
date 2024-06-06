package com.tony.fast.architecture.model.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DictSelectReq {
    @ApiModelProperty(value = "类型", example = "1", required = true)
    @NotNull(message = "类型不能为空")
    private Integer type;
    @ApiModelProperty(value = "筛选父级编码", example = "WJ")
    private String parentCode;
    @ApiModelProperty(value = "筛选编码", example = "WJ")
    private String code;
}
