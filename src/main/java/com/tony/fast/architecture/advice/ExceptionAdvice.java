package com.tony.fast.architecture.advice;

import com.tony.fast.architecture.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数JSON解析错误: ", e);
        return R.error("参数JSON解析错误");
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error("发生异常: ", e);
        return R.error(e.getMessage());
    }

    /* 添加校验参数异常处理 */
    @ExceptionHandler(BindException.class)
    public R bindExceptionHandler(BindException e) {
        log.error("参数错误: ", e);
        return R.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
