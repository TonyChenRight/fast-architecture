package com.tony.fast.architecture.model;

import lombok.Data;

import java.util.Date;


@Data
public class AccessLog {
    private Long id;

    /**
     * 访问用户
     */
    private Long accessUser;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private Object params;

    /**
     * 执行时长(毫秒)
     */
    private Date executeTime;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date createTime;

}