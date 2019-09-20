package com.wechatapp.eticket.core.entity;

import com.wechatapp.eticket.core.enums.TicketTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "eticket_tickettype")
@EqualsAndHashCode(callSuper = false)
public class TicketTypeEntity extends BaseEntity {

	// 种类编号
	@Id
	@Column(name = "type_id")
	private Long typeId;

	// 票务种类
	@Column(name = "ticket_type")
	@Enumerated(EnumType.STRING)
	private TicketTypeEnum ticketType;
	
	// 种类名称
	@Column(name = "type_name")
	private String typeName;
}
