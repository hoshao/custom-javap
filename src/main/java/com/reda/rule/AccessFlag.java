package com.reda.rule;

public enum AccessFlag {
    // 0x0001
    ACC_PUBLIC(1),
    // 0x0010
    ACC_FINAL(16),
    // 0x0020
    ACC_SUPER(32),
    // 0x0200
    ACC_INTERFACE(512),
    // 0x0400
    ACC_ABSTRACT(1024),
    // 0x1000
    ACC_SYNTHETIC(4096),
    // 0x2000
    ACC_ANNOTATION(8192),
    // 0x4000
    ACC_ENUM(16384);

    private int flag;

    AccessFlag(int flag) {
        this.flag = flag;
    }
}
