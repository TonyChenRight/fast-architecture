package com.tony.fast.architecture.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionType {
    MENU(1),
    API(2);

    private Integer val;
}
