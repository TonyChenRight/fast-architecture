package com.tony.fast.architecture.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.fast.architecture.domain.Dict;
import com.tony.fast.architecture.model.dict.DictSelect;
import com.tony.fast.architecture.model.dict.DictSelectReq;
import com.tony.fast.architecture.service.DictService;
import com.tony.fast.architecture.mapper.DictMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author tonychen
* @description 针对表【dict(字典表)】的数据库操作Service实现
* @createDate 2024-06-05 19:14:49
*/
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict>
    implements DictService{

    @Override
    public List<DictSelect> selectDict(DictSelectReq req) {
        LambdaQueryWrapper<Dict> queryWrapper = Wrappers.lambdaQuery(Dict.class)
                .eq(Dict::getType, req.getType())
                .eq(StrUtil.isNotBlank(req.getParentCode()), Dict::getParentCode, req.getParentCode())
                .eq(StrUtil.isNotBlank(req.getCode()), Dict::getCode, req.getCode())
                .orderByDesc(Dict::getWeight);
        return list(queryWrapper).stream().map(DictSelect::new).collect(Collectors.toList());
    }
}




