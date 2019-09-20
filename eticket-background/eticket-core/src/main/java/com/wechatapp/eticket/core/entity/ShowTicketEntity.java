package com.wechatapp.eticket.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 电影、表演、展览票券
 * 
 * @author MSI
 *
 */
@Data
@Entity
@Table(name = "eticket_showticket")
@EqualsAndHashCode(callSuper = false)
public class ShowTicketEntity extends BaseEntity {

	// 分库主键
	@Id
	@Column(name = "sharding_id")
	private String shardingId;

	// 手机号码
	@Column(name = "telephone_number")
	private String telephoneNumber;

	// 订单编号
	@Column(name = "order_id")
	private Long orderId;

	// 支付编号
	@Column(name = "payment_id")
	private Long paymentId;

	// 种类编号
	@Column(name = "type_id")
	private Long typeId;

	// 券码的真正价格
	@Column(name = "price")
	private BigDecimal price;
	
	// 手续费
	@Column(name = "service_charge")
	private BigDecimal serviceCharge;
		
	// 手续费+券码价格=总价格
	@Column(name = "total_price")
	private BigDecimal totalPrice;

	// 是否需要上传二维码
	@Column(name = "upload_flag")
	private Boolean uploadFlag;

	// 订单图片地址
	@Column(name = "img_address")
	private String imgAddress;

	// 券码二维码
	@Column(name = "qrcode")
	private String qrCode;

	// 出售或者求购  0:表示出售  1：表示求购
	@Column(name = "sale_or_buy_flag")
	private String saleOrBuyFlag;
}
