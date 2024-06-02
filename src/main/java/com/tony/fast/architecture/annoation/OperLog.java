package com.tony.fast.architecture.annoation;

import com.tony.fast.architecture.enums.OperationModule;
import com.tony.fast.architecture.enums.OperationType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    OperationModule module() default OperationModule.NONE;
    OperationType type() default OperationType.NONE;
}
