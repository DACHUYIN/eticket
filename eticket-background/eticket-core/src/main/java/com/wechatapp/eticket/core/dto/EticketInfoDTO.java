package com.wechatapp.eticket.core.dto;

import com.wechatapp.eticket.core.enums.OrderStatusEnum;
import com.wechatapp.eticket.core.enums.OrderTypeEnum;
import com.wechatapp.eticket.core.enums.PaymentStatusEnum;
import com.wechatapp.eticket.core.enums.TicketTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author virgo.zx
 * @date 2019/8/18 17:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EticketInfoDTO {

    // 买家-微信标识
    private String wechatOpenIdBuyer;

    // 卖家-微信标识
    private String wechatOpenIdSeller;

    // 卖家-手机号码
    private String telephoneNumber;

    // 订单类型
    private OrderTypeEnum orderType;

    // 订单状态
    private OrderStatusEnum orderStatus;

    // 票务种类
    private TicketTypeEnum ticketType;

    // 票务名称
    private String ticketName;

    // 价格
    private BigDecimal price;

    // 手续费
    private BigDecimal serviceCharge;

    // 最终价格
    private BigDecimal totalPrice;

    // 是否上传图片
    private String uploadFlag;

    // 图片地址
    private String imgAddress;

    // 二维码
    private String qrCode;

    // 券码有效期开始日
    private String startDate;

    // 券码有效期截止日
    private String endDate;

    // 券码有效天数
    private int termValidity;

    // 储存在redis中的每一条交易信息的key
    // 用来后续将redis中的数据和MySQL中的数据关联起来
    private String redisMapKey;

    // SQL是执行插入，还是更新亦或是删除操作
    private int sqlMethod;

    // 交易状态
    private PaymentStatusEnum paymentStatus;
}
