package com.wechatapp.eticket.core.entity;

import com.wechatapp.eticket.core.enums.OrderStatusEnum;
import com.wechatapp.eticket.core.enums.OrderTypeEnum;
import com.wechatapp.eticket.core.enums.TicketTypeEnum;
import lombok.*;

import java.math.BigDecimal;

import javax.persistence.*;

@Data
@Entity
@Table(name = "eticket_order")
@EqualsAndHashCode(callSuper = false)
@IdClass(OrderEntityPK.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity extends BaseEntity {

	// 主键---也用于分库分表
	@Id
	@Column(name = "sharding_id")
	private String shardingId;
	
	// 订单编号
	@Id
	@Column(name = "order_id")
	private Long orderId;

	// 手机号码---ymp分库分表中用这列进行的
	@Column(name = "telephone_number")
	private String telephoneNumber;

	// redis中的交易主键
	@Column(name = "redis_map_key")
	private String redisMapKey;

	// 微信标识-买家
	@Column(name = "wechat_openid_buyer")
	private String wechatOpenIdBuyer;

	// 微信标识-卖家
	@Column(name = "wechat_openid_seller")
	private String wechatOpenIdSeller;

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
	@Column(name = "ticket_name")
	private String ticketName;

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
