package com.wechatapp.eticket.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberShipDTO {

	// 买家-微信标识
	private String wechatOpenIdBuyer;

	// 卖家-微信标识
	private String wechatOpenIdSeller;
	
	// 信用积分
	private Integer creditPoint;
}
