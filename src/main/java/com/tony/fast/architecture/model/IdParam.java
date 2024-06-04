package com.tony.fast.architecture.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IdParam {
    @ApiModelProperty(value = "ID", example = "1", required = true)
    @NotNull(message = "ID不能为空")
    private Long id;
}
