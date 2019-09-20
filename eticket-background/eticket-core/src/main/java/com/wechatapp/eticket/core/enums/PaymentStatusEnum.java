package com.wechatapp.eticket.core.enums;

/**
 * -1:核查中，表示该电子票券交易存在纠纷
 * 1:确认中，支付完成，但是尚未确认收货
 * 2:交易成功
 *
 * @author virgo.zx
 * @date 2019/8/19 20:59
 */
public enum PaymentStatusEnum {

    VERIFYING(-1, "核查中"),CHECKING(1, "确认中"),SUCCESS(2, "交易成功");

    private Byte value;
    private String description;

    PaymentStatusEnum(int value, String description) {
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
