package com.wechatapp.eticket.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.wechatapp.eticket.core.enums.PaymentStatusEnum;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "eticket_payment")
@EqualsAndHashCode(callSuper = false)
@IdClass(PaymentEntityPK.class)
public class PaymentEntity extends BaseEntity {
	
	// 分库主键
	@Id
	@Column(name = "sharding_id")
	private String shardingId;
	
	// 支付编号
	@Id
	@Column(name = "payment_id")
	private Long paymentId;

	// 手机号码
	@Column(name = "telephone_number")
	private String telephoneNumber;

	// 微信标识
	@Column(name = "wechat_openid")
	private String wechatOpenId;
	
	// 订单编号
	@Column(name = "order_id")
	private Long orderId;

	// 券码的真正价格
	@Column(name = "price")
	private BigDecimal price;
	
	// 手续费
	@Column(name = "service_charge")
	private BigDecimal serviceCharge;
		
	// 手续费+券码价格=总价格
	@Column(name = "total_price")
	private BigDecimal totalPrice;

	// 支付状态
	@Column(name = "payment_status")
	@Enumerated(EnumType.ORDINAL)
	private PaymentStatusEnum paymentStatus;
}
