package com.tony.fast.architecture.service;

import com.tony.fast.architecture.domain.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tony.fast.architecture.model.dict.DictSelect;
import com.tony.fast.architecture.model.dict.DictSelectReq;

import java.util.List;

/**
* @author tonychen
* @description 针对表【dict(字典表)】的数据库操作Service
* @createDate 2024-06-05 19:14:49
*/
public interface DictService extends IService<Dict> {

    List<DictSelect> selectDict(DictSelectReq req);
}
