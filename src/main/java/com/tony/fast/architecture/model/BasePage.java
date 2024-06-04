
package com.tony.fast.architecture.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePage {
    @ApiModelProperty(value = "页码", example = "1")
    private Integer current = 1;
    @ApiModelProperty(value = "页大小", example = "10")
    private Integer size = 10;
}
