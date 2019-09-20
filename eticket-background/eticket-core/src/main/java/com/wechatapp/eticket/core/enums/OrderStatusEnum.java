package com.wechatapp.eticket.core.enums;

/**
 * 0，求购中：表明该订单为求购订单
 * 1，出售中：表明该订单为出售订单
 * 2，交易中：表明该订单处于交易中，尚未确认收货
 * 3，完成：表明该订单已确认收货
 * 4，已关闭：表明该订单已经关闭，会员不想出售或者求购了
 * @author virgo.zx
 * @date 2019/8/20 21:16
 */
public enum OrderStatusEnum {

    BUYING(0, "求购中"), SELLING(1, "出售中"), DEALING(2, "交易中"),
    COMPLETING(3, "完成"), CLOSING(4, "已关闭");

    private Byte value;
    private String description;

    OrderStatusEnum(int value, String description) {
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
