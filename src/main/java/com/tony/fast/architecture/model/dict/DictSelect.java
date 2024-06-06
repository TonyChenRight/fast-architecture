package com.tony.fast.architecture.model.dict;

import cn.hutool.core.bean.BeanUtil;
import com.tony.fast.architecture.domain.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictSelect {
    @ApiModelProperty(value = "类型", example = "ccc")
    private Integer type;
    @ApiModelProperty(value = "编码", example = "ccc")
    private String code;
    @ApiModelProperty(value = "父级编码", example = "xxx")
    private String parentCode;
    @ApiModelProperty(value = "名称", example = "名称")
    private String name;
    @ApiModelProperty(value = "权重", example = "1")
    private Integer weight;

    public DictSelect(Dict dict) {
        BeanUtil.copyProperties(dict, this);
    }
}
