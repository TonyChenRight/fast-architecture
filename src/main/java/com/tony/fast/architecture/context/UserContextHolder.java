package com.tony.fast.architecture.context;

import com.tony.fast.architecture.model.UserInfo;

public class UserContextHolder {
    private static final ThreadLocal<UserInfo> CONTEXT = ThreadLocal.withInitial(UserInfo::new);

    public static void setUser(UserInfo user) {
        CONTEXT.set(user);
    }
    public static UserInfo getUser() {
        return CONTEXT.get();
    }
    public static void clear() {
        CONTEXT.remove();
    }
}
