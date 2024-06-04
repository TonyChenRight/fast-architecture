package com.tony.fast.architecture.utils;

import cn.hutool.core.lang.Assert;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class I18nUtil {

    public static String getMessage(String code) {
        MessageSource messageSource = SpringContextUtils.getBean(MessageSource.class);
        Assert.notNull(messageSource, "缺少 `messageSource` bean");
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
