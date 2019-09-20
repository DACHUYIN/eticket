package com.wechatapp.eticket.core.common.constants;

public class RedisConstant {

	private RedisConstant() {
	}

	/**
	 * 微信OpenId
	 */
	public static final String REDIS_WECHATOPENID = "wechatOpenId";
	
	/**
	 * 昵称
	 */
	public static final String REDIS_NICKNAME = "nickName";
	
	/**
	 * 头像地址
	 */
	public static final String REDIS_AVATARURL = "avatrUrl";
	
	/**
	 * 信用评分
	 */
	public static final String REDIS_CREDITPOINT = "creditPoint";
	
	/**
	 * 会员等级
	 */
	public static final String REDIS_MEMBERSHIPLEVEL = "membershipLevel";
	
	/**
	 * 手机号码
	 */
	public static final String REDIS_TELEPHONENUMBER = "telephoneNumber";
	
	/**
	 * token
	 */
	public static final String REDIS_TOKEN = "token";
	
	/**
	 * 最近一次的登录时间
	 */
	public static final String REDIS_LATESTLOGINTIME = "latestLoginTime";
}
