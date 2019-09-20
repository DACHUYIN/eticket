package com.wechatapp.eticket.core.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberEntityPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1130793638692062519L;

	private String shardingId;
	
	private Long userId;
}
