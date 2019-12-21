package com.wechatapp.eticket.core.common.constants;

public class RedisConstant {

	private RedisConstant() {
	}

	/**
	 * 会员redis数据
	 */
	public static final String REDIS_MEMBER = "Member:";

	/**
	 * 交易redis数据
	 */
	public static final String REDIS_TRANSACTION = "Transaction:";

	/**
	 * RocketMQ日志redis数据
	 */
	public static final String REDIS_ROCKETMQLOG = "RocketMQLog:";

	/**
	 * RocketMQ日志消息
	 */
	public static final String REDIS_ROCKETMQMESSAGE = "RocketMQMessage";

	/**
	 * 微信OpenId
	 */
	public static final String REDIS_WECHATOPENID = "wechatOpenId";

	/**
	 * 出售市场
	 */
	public static final String REDIS_MARKET_SELL = "marketSell:";

	/**
	 * 求购市场
	 */
	public static final String REDIS_MARKET_BUY = "marketBuy:";

	/**
	 * 餐饮券市场
	 */
	public static final String REDIS_MARKET_FOOD = "marketFood:";

	/**
	 * 影音娱乐券市场
	 */
	public static final String REDIS_MARKET_ENTERTAINMENT = "marketEntertainment:";

	/**
	 * 景点门票市场
	 */
	public static final String REDIS_MARKET_ADMINSION = "marketAdminsion:";

	/**
	 * 电影门票市场
	 */
	public static final String REDIS_MARKET_FILM = "marketFilm:";

	/**
	 * 音乐会门票市场
	 */
	public static final String REDIS_MARKET_CONCERT = "marketConcert:";

	/**
	 * 展览门票市场
	 */
	public static final String REDIS_MARKET_SHOW = "marketShow:";
	
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

	/**
	 * 校验幂等性的token
	 */
	public static final String REDIS_IDEMPOTENT_TOKEN = "idempotentToken:";
}
