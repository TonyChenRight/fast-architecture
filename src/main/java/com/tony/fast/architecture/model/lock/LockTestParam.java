package com.tony.fast.architecture.model.lock;

import lombok.Data;

@Data
public class LockTestParam {
    private String key;
    private Long timeout;
}
