package com.tony.fast.architecture.controller.common;

import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.dict.DictSelect;
import com.tony.fast.architecture.model.dict.DictSelectReq;
import com.tony.fast.architecture.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Validated
@Api(tags = "公共下拉接口")
@RequestMapping("/common/select")
@RestController
public class SelectController {

    @Resource
    private DictService dictService;

    @ApiOperation("字典下拉")
    @GetMapping("/dict")
    public R<List<DictSelect>> selectDict(@Validated DictSelectReq req) {
        return R.ok(dictService.selectDict(req));
    }
}
