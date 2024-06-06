package com.tony.fast.architecture.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusType {
    DISABLE(0),
    ENABLE(1),
    ;

    private Integer val;
}
