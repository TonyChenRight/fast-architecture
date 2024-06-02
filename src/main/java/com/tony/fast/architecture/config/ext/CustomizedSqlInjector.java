package com.tony.fast.architecture.config.ext;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.tony.fast.architecture.constant.Constants;
import org.apache.ibatis.session.Configuration;

import java.util.List;

public class CustomizedSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(configuration, mapperClass, tableInfo);
        methodList.add(new InsertBatchMethod(Constants.MYBATIS_EXT_INSERT_BATCH_METHOD));
        methodList.add(new UpdateBatchMethod(Constants.MYBATIS_EXT_UPDATE_BATCH_METHOD));
        return methodList;
    }
}