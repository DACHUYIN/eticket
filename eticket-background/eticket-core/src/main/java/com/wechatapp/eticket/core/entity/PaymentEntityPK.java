package com.wechatapp.eticket.core.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class PaymentEntityPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5330848388288510274L;

	private String shardingId;
	
	private Long paymentId;
}
