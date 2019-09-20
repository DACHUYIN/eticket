package com.wechatapp.eticket.member.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class WechatAuthDTO {

	@Value("${auth.wechat.sessionHost}")
	private String sessionHost;

	@Value("${auth.wechat.appId}")
	private String appId;

	@Value("${auth.wechat.secret}")
	private String secret;

	@Value("${auth.wechat.grantType}")
	private String grantType;

}
