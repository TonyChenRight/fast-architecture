package com.tony.fast.architecture.model;

import com.tony.fast.architecture.enums.OperationModule;
import com.tony.fast.architecture.enums.OperationType;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class OperationLogContext{
    private OperationModule module;
    private OperationType type;
    private Set<String> targetIds;
    private String remark;

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void addTargetId(String targetId) {
        this.targetIds = targetIds == null ? new HashSet<>() : targetIds;
        targetIds.add(targetId);
    }
}
