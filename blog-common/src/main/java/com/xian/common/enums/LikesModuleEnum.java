package com.xian.common.enums;

public enum LikesModuleEnum {
    BLOG("博客"),
    ACTIVITY("活动");

    private String value;

    public String getValue() {
        return value;
    }

    LikesModuleEnum(String value) {
        this.value = value;
    }
}
