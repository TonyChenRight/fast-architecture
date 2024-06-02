package com.tony.fast.architecture.utils;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import java.util.Properties;

public class KaptchaUtil {
    private static final Integer DEFAULT_WIDTH = 200;
    private static final Integer DEFAULT_HEIGHT = 50;

    public static Producer buildProducer(Integer width, Integer height) {
        width = width == null ? DEFAULT_WIDTH : width;
        height = height == null ? DEFAULT_HEIGHT : height;
        Properties properties = new Properties();
        //设置图片边框
        properties.setProperty("kaptcha.border", "yes");
        //设置图片边框为蓝色
        properties.setProperty("kaptcha.border.color", "blue");
        //背景颜色渐变开始
        properties.put("kaptcha.background.clear.from", "127,255,212");
        //背景颜色渐变结束
        properties.put("kaptcha.background.clear.to", "240,255,255");
        // 字体颜色
        properties.put("kaptcha.textproducer.font.color", "black");
        // 文字间隔
        properties.put("kaptcha.textproducer.char.space", "10");
        //如果需要去掉干扰线
        properties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        // 字体
        properties.put("kaptcha.textproducer.font.names", "Arial,Courier,cmr10,宋体,楷体,微软雅黑");
        // 图片宽度
        properties.setProperty("kaptcha.image.width", width.toString());
        // 图片高度
        properties.setProperty("kaptcha.image.height", height.toString());
        // 从哪些字符中产生
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        // 字符个数
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(new Config(properties));
        return defaultKaptcha;
    }
}
