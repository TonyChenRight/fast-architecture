package com.tony.fast.architecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.fast.architecture.domain.Dict;
import com.tony.fast.architecture.service.DictService;
import com.tony.fast.architecture.mapper.DictMapper;
import org.springframework.stereotype.Service;

/**
* @author tonychen
* @description 针对表【dict(字典表)】的数据库操作Service实现
* @createDate 2024-06-05 19:14:49
*/
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict>
    implements DictService{

}




