package com.wechatapp.eticket.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 展览会门票
 * 
 * @author virgo.zx
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

	// 订单编号
	@Column(name = "order_id")
	private Long orderId;

	// 手机号码---ymp分库分表中用这列进行的
	@Column(name = "telephone_number")
	private String telephoneNumber;

	// 种类编号
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

	// 是否需要上传二维码--- 1:表示上传，-1：表示不上传
	@Column(name = "upload_flag")
	private String uploadFlag;

	// 订单图片地址
	@Column(name = "img_address")
	private String imgAddress;

	// 券码二维码
	@Column(name = "qr_code")
	private String qrCode;

	// 展览举办城市名称
	@Column(name = "city_name")
	private String cityName;

	// 展览馆名称
	@Column(name = "show_hall_name")
	private String showHallName;

	// 展览名称
	@Column(name = "show_name")
	private String showName;

	// 展览开始时间
	@Column(name = "start_date")
	private Date startDate;
}
