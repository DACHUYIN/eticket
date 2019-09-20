package com.wechatapp.eticket.core.enums;

/**
 * 0，求购订单
 * 1，出售订单
 * @author virgo.zx
 * @date 2019/8/20 21:23
 */
public enum OrderTypeEnum {

    SELL(0, "出售"), BUY(1, "求购");

    private Byte value;
    private String description;

    OrderTypeEnum(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    public Byte getValue() {
        return this.value;
    }

    public String getName() {
        return this.description;
    }
}
