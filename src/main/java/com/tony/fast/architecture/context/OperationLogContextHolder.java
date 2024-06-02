package com.tony.fast.architecture.context;

import com.tony.fast.architecture.model.OperationLogContext;

public class OperationLogContextHolder {
    private static final ThreadLocal<OperationLogContext> THREAD_LOCAL =ThreadLocal.withInitial(OperationLogContext::new);

    public static OperationLogContext get() {
        return THREAD_LOCAL.get();
    }

    public static void set(OperationLogContext context) {
        THREAD_LOCAL.set(context);
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
