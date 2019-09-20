package com.wechatapp.eticket.transaction.dto;

import com.wechatapp.eticket.core.enums.TicketTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

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

    // 订单编号
    private Long orderId;

    // 支付编号
    private Long paymentId;

    // 种类编号
    private Long typeId;

    // 种类名称
    private String typeName;

    // 券码类型
    private TicketTypeEnum ticketType;

    // 价格
    private BigDecimal price;

    // 手续费
    private BigDecimal serviceCharge;

    // 最终价格
    private BigDecimal totalPrice;

    // 是否上传图片
    private Boolean uploadFlag;

    // 图片地址
    private String imgAddress;

    // 二维码
    private String qrCode;

    // 券码有效期开始日
    private Date startDate;

    // 券码有效期截止日
    private Date endDate;

    // 券码有效天数
    private Integer termValidity;

    // 求购或者出售标记
    private String saleOrBuyFlag;
}
