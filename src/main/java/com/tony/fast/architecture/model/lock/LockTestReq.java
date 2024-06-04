package com.tony.fast.architecture.model.lock;

import lombok.Data;

@Data
public class LockTestReq {
    private String key;
    private Long timeout;
}
