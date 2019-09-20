package com.wechatapp.eticket.core.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderEntityPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6336429106614285177L;

	private String shardingId;
	
	private Long orderId;
}
