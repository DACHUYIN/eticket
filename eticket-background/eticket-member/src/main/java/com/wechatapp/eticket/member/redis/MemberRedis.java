package com.wechatapp.eticket.member.redis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.wechatapp.eticket.core.common.constants.JwtConstant;
import com.wechatapp.eticket.core.common.constants.RedisConstant;
import com.wechatapp.eticket.core.common.constants.ResponseMsgConstant;
import com.wechatapp.eticket.core.common.util.DateFormatUtils;
import com.wechatapp.eticket.core.common.util.JwtOperatorUtils;
import com.wechatapp.eticket.core.enums.MemberShipLevelEnum;
import com.wechatapp.eticket.member.dto.JwtTokenResponseDTO;
import com.wechatapp.eticket.member.dto.LoginResponseDTO;
import com.wechatapp.eticket.member.dto.UserInfoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 把会员的一些信息存到Redis中去
 * 
 * @author virgo.zx
 * @date 2019/8/24 21:38
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberRedis {

	private final StringRedisTemplate stringRedisTemplate;


	/**
	 * 查询用户是否处于登录状态
	 * 
	 * @param wechatOpenId
	 * @param userInfoDTO
	 * @return
	 */
	public LoginResponseDTO updateLoginInfo(String wechatOpenId) {
		log.info("开始从Redis查询登录数据");
		// redis中对应用户缓存的一些用户信息
		Map<Object, Object> cacheData = stringRedisTemplate.opsForHash().entries(wechatOpenId);
		if (null != cacheData && cacheData.size() > 0) {
			log.info("微信OpenId为{}的用户在Redis中有既存的token和数据", wechatOpenId);
			int creditPoint = Integer.valueOf((String) cacheData.get(RedisConstant.REDIS_CREDITPOINT));
			MemberShipLevelEnum memberShipLevel = getMemberShipLevel((String) cacheData.get(RedisConstant.REDIS_MEMBERSHIPLEVEL));
			String nickName = (String) cacheData.get(RedisConstant.REDIS_NICKNAME);
			String avatarUrl = (String) cacheData.get(RedisConstant.REDIS_AVATARURL);
			String telephoneNumber = (String) cacheData.get(RedisConstant.REDIS_TELEPHONENUMBER);
			Map<String, Object> userInfo = new HashMap<>(4);
			userInfo.put(RedisConstant.REDIS_WECHATOPENID, wechatOpenId);
			userInfo.put(RedisConstant.REDIS_CREDITPOINT, creditPoint);
			userInfo.put(RedisConstant.REDIS_MEMBERSHIPLEVEL, memberShipLevel.toString());
			userInfo.put(RedisConstant.REDIS_TELEPHONENUMBER, telephoneNumber);

			String token  = JwtOperatorUtils.generateToken(userInfo);
			log.info("用户{}登录成功，生成的token = {}, 有效期到:{}", nickName, token,
					JwtOperatorUtils.getExpirationTime());
			// 删除该用户的上一次的缓存数据
			stringRedisTemplate.delete(wechatOpenId);
			// 再一次更新Redis中的数据
			saveLoginInfoInRedis(wechatOpenId, nickName, avatarUrl, 
					creditPoint, memberShipLevel, telephoneNumber, token);
			
			// 构建响应
			return LoginResponseDTO.builder()
					.token(JwtTokenResponseDTO.builder()
							.expirationTime(JwtOperatorUtils.getExpirationTime().getTime())
							.token(token)
							.build())
					.userInfoDTO(UserInfoDTO.builder().wechatOpenId(wechatOpenId) // 微信标识
							.nickName(nickName) // 昵称
							.avatarUrl(avatarUrl) // 头像地址
							.creditPoint(creditPoint) // 信用评分
							.memberShipLevel(memberShipLevel) // 会员等级
							.telephoneNumber(telephoneNumber) // 手机号码
							.build())
					.responseCode(ResponseMsgConstant.RESPONSECODE_SUCCESS_LOGIN) // 返回成功的登录Code
					.build();
		}
		return LoginResponseDTO.builder()
				    .responseCode(ResponseMsgConstant.RESPONSECODE_FAIL_LOGIN_REDISERROR)
				    .responseMsg(ResponseMsgConstant.RESPONSEMSG_FAIL_LOGIN_ERROR)
				    .build();
	}

	/**
	 * 根据wechatOpenId,保存用户的登录token
	 * 
	 * @param wchatOpenId
	 * @param token
	 */
	public void saveLoginInfoInRedis(String wchatOpenId, String nickName, String avatarUrl, Integer creditPoint, 
			MemberShipLevelEnum memberShipLevel, String telephoneNumber, String token) {
        log.info("把用户数据保存到以Hash的形式保存到Redis中");
		Map<String, Object> redisMap = new HashMap<>();
        redisMap.put(RedisConstant.REDIS_NICKNAME, nickName);
		redisMap.put(RedisConstant.REDIS_AVATARURL, avatarUrl);
		redisMap.put(RedisConstant.REDIS_CREDITPOINT, String.valueOf(creditPoint));
		redisMap.put(RedisConstant.REDIS_MEMBERSHIPLEVEL, memberShipLevel.toString());
		redisMap.put(RedisConstant.REDIS_LATESTLOGINTIME, DateFormatUtils.YYYY_MM_DD_HH_MM_SS.get().format(new Date()));
		redisMap.put(RedisConstant.REDIS_TELEPHONENUMBER, telephoneNumber);
		redisMap.put(RedisConstant.REDIS_TOKEN, token);
		log.info("存储到Redis中的该用户ID为：" +wchatOpenId);
		stringRedisTemplate.opsForHash().putAll(wchatOpenId, redisMap);
		// 设置过期时间，和token的过期时间保持一致，都是2周
		stringRedisTemplate.expire(wchatOpenId, JwtConstant.EXPIRATION_TIME_IN_SECOND, TimeUnit.SECONDS);
	}

	/**
	 * 获取会员类型
	 * 
	 * @param str
	 * @return
	 */
	private MemberShipLevelEnum getMemberShipLevel(String str) {
		if (str.equals(MemberShipLevelEnum.NORMAL.toString())) {
			return MemberShipLevelEnum.NORMAL;
		} else if (str.equals(MemberShipLevelEnum.SILVER.toString())) {
			return MemberShipLevelEnum.SILVER;
		} else if (str.equals(MemberShipLevelEnum.GOLDEN.toString())) {
			return MemberShipLevelEnum.GOLDEN;
		} else {
			return MemberShipLevelEnum.DIAMOND;
		}
	}
}
