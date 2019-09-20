package com.wechatapp.eticket.core.entity;

import com.wechatapp.eticket.core.enums.OrderStatusEnum;
import com.wechatapp.eticket.core.enums.OrderTypeEnum;
import com.wechatapp.eticket.core.enums.TicketTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Table(name = "eticket_order")
@EqualsAndHashCode(callSuper = false)
@IdClass(OrderEntityPK.class)
public class OrderEntity extends BaseEntity {

	// 分库主键
	@Id
	@Column(name = "sharding_id")
	private String shardingId;
	
	// 订单编号
	@Id
	@Column(name = "order_id")
	private Long orderId;

	// 手机号码
	@Column(name = "telephone_number")
	private String telephoneNumber;

	// 微信标识
	@Column(name = "wechat_openid")
	private String wechatOpenId;
	
	// 订单类型
	@Column(name = "order_type")
	@Enumerated(EnumType.STRING)
	private OrderTypeEnum orderType;

	// 订单状态
	@Column(name = "order_status")
	@Enumerated(EnumType.ORDINAL)
	private OrderStatusEnum orderStatus;

	// 票务种类
	@Column(name = "ticket_type")
	@Enumerated(EnumType.STRING)
	private TicketTypeEnum ticketType;

	// 种类名称
	@Column(name = "type_name")
	private String typeName;

	// 券码的真正价格
	@Column(name = "price")
	private BigDecimal price;
	
	// 手续费
	@Column(name = "service_charge")
	private BigDecimal serviceCharge;
		
	// 手续费+券码价格=总价格
	@Column(name = "total_price")
	private BigDecimal totalPrice;
}
