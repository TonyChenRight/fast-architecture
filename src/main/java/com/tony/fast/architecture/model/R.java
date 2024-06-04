package com.tony.fast.architecture.model;

import com.tony.fast.architecture.constant.Codes;
import com.tony.fast.architecture.utils.I18nUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class R<T> {
    @ApiModelProperty(value = "响应码", example = "200")
    private String code;
    @ApiModelProperty(value = "错误信息", example = "xxxx")
    private String message;
    @ApiModelProperty(value = "响应业务数据")
    private T data;
    @ApiModelProperty(value = "是否成功", example = "true")
    private boolean success;

    public static <E> R<E> ok() {
        R<E> result=new R<>();
        result.code = Codes.SUCCESS;
        return result;
    }
    public static <E> R<E> ok(E data) {
        R<E> result=new R<>();
        result.code = Codes.SUCCESS;
        result.data = data;
        return result;
    }

    public static <E> R<E> error(String code) {
        R<E> result=new R<>();
        result.code = code;
        result.message = I18nUtil.getMessage(code);
        return result;
    }

    public static <E> R<E> sysError(String message) {
        R<E> result=new R<>();
        result.code = Codes.SYSTEM_ERROR;
        result.message = message;
        return result;
    }

    public static <E> R<E> error(String code, String message) {
        R<E> result=new R<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public boolean isSuccess() {
        return Objects.equals(code, Codes.SUCCESS);
    }
}
