package com.wechatapp.eticket.member.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.wechatapp.eticket.core.common.constants.CommonConstant;
import com.wechatapp.eticket.core.common.constants.MemberShipConstant;
import com.wechatapp.eticket.core.common.constants.RedisConstant;
import com.wechatapp.eticket.core.common.constants.ResponseMsgConstant;
import com.wechatapp.eticket.core.common.util.JwtOperatorUtils;
import com.wechatapp.eticket.core.common.util.ShardingUtils;
import com.wechatapp.eticket.core.dto.MemberShipDTO;
import com.wechatapp.eticket.core.entity.MemberEntity;
import com.wechatapp.eticket.core.enums.MemberShipLevelEnum;
import com.wechatapp.eticket.core.repository.MemberRepository;
import com.wechatapp.eticket.member.dto.*;
import com.wechatapp.eticket.member.redis.MemberRedis;
import com.wechatapp.eticket.member.service.IMemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberServiceImpl implements IMemberService {

	private final WxMaService wxMaService;

	private final MemberRepository memberRepository;

	private final MemberRedis memberRedis;
	
	/**
	 * 用户登录
	 *
	 * @param userInfoDTO
	 * @return
	 */
	@Override
	public LoginResponseDTO login(UserInfoDTO userInfoDTO) {
		
		try {
			log.info("开始进行登录服务");
			// 微信小程序服务端校验是否已经登录的结果
			WxMaJscode2SessionResult result = this.wxMaService.getUserService().getSessionInfo(userInfoDTO.getCode());
			// 微信的openId，用户在微信这边的唯一标示
			String wechatOpenId = result.getOpenid();
			log.info("开始持久化用户数据");
			MemberEntity memberEntity = CheckIsMember(userInfoDTO, wechatOpenId);
			log.info("用户数据持久化完成");
			// 创造用户登录识别码token
			Map<String, Object> userInfo = new HashMap<>(4);
			userInfo.put(RedisConstant.REDIS_WECHATOPENID, memberEntity.getWechatOpenId());
			userInfo.put(RedisConstant.REDIS_CREDITPOINT, memberEntity.getCreditPoint());
			userInfo.put(RedisConstant.REDIS_MEMBERSHIPLEVEL, memberEntity.getMemberShipLevel());
            userInfo.put(RedisConstant.REDIS_TELEPHONENUMBER, memberEntity.getTelephoneNumber());
			
			String token  = JwtOperatorUtils.generateToken(userInfo);
			log.info("用户{}登录成功，生成的token = {}, 有效期到:{}", memberEntity.getNickName(), token,
					JwtOperatorUtils.getExpirationTime());

			// 用户登录完成后把相应的数据存储到Redis中
			memberRedis.saveLoginInfoInRedis(wechatOpenId, memberEntity.getNickName(), memberEntity.getAvatarUrl(),
					memberEntity.getCreditPoint(), memberEntity.getMemberShipLevel(), memberEntity.getTelephoneNumber(), token);
			
			// 构建响应
			return LoginResponseDTO.builder()
					.token(JwtTokenResponseDTO.builder()
							.expirationTime(JwtOperatorUtils.getExpirationTime().getTime())
							.token(token)
							.build())
					.userInfoDTO(UserInfoDTO.builder().wechatOpenId(wechatOpenId) // 微信标识
							.nickName(userInfoDTO.getNickName()) // 昵称
							.avatarUrl(userInfoDTO.getAvatarUrl()) // 头像地址
							.creditPoint(memberEntity.getCreditPoint()) // 信用评分
							.memberShipLevel(memberEntity.getMemberShipLevel()) // 会员等级
							.telephoneNumber(memberEntity.getTelephoneNumber()) // 手机号码
							.build())
					.responseCode(ResponseMsgConstant.RESPONSECODE_SUCCESS_LOGIN) // 返回成功的登录Code
					.build();
		} catch (WxErrorException e) {
			log.error("与微信服务器连接报错" + e);
			return LoginResponseDTO.builder()
					.responseCode(ResponseMsgConstant.RESPONSECODE_FAIL_LOGIN_WXERROR)
					.responseMsg(ResponseMsgConstant.RESPONSEMSG_FAIL_LOGIN_WXERROR)
					.build();
		} catch (Exception e) {
			log.error(e.toString());
			return LoginResponseDTO.builder()
					.responseCode(ResponseMsgConstant.RESPONSECODE_FAIL_LOGIN_ERROR)
					.responseMsg(ResponseMsgConstant.RESPONSEMSG_FAIL_LOGIN_ERROR)
					.build();
		}
	}

	/**
	 * 更新已经登录用户的token，并延长登录用户的登录有效期
	 *
	 * @param wechatOpenId
	 * @return LoginResponseDTO
	 * @throws WxErrorException
	 */
	@Override
	public LoginResponseDTO updateLoginUserInfo(String wechatOpenId) {
		log.info("更新已经登录用户的token，并延长登录用户的登录有效期");
		try {
			LoginResponseDTO loginResponseDTO = memberRedis.updateLoginInfo(wechatOpenId);
			return loginResponseDTO;
		} catch (Exception e) {
			log.error(e.toString());
			return LoginResponseDTO.builder()
					.responseCode(ResponseMsgConstant.RESPONSECODE_FAIL_LOGIN_ERROR)
					.responseMsg(ResponseMsgConstant.RESPONSEMSG_FAIL_LOGIN_ERROR)
					.build();
		}
	}

	/**
	 * 检查登录的是否是会员，不是会员进行注册，是会员的话更新相关信息
	 *
	 * @param userInfoDTO
	 * @param wechatOpenId
	 */
	private MemberEntity CheckIsMember(UserInfoDTO userInfoDTO, String wechatOpenId) {
		MemberEntity existMemberEntity = memberRepository.getMemberByWechatOpenId(wechatOpenId);
		if (null != existMemberEntity) {
			memberRepository.updateMemberEntity(userInfoDTO.getNickName(), userInfoDTO.getGender(),
			userInfoDTO.getAvatarUrl(), wechatOpenId);
			log.info("用户数据更新成功");
		} else {
			String telephoneNumber = "176211970" + ((int) (Math.random() * 90) + 10);
			String shardingId = ShardingUtils.generateShardingId(telephoneNumber);
			MemberEntity memberEntity = MemberEntity.builder()
					.shardingId(shardingId) // 分库主键
					.nickName(userInfoDTO.getNickName()) // 昵称
					.memberShipLevel(MemberShipLevelEnum.NORMAL) // 默认为普通会员
					.gender(userInfoDTO.getGender()) // 性别
					.blacklistFlag(CommonConstant.NOT_BLACKMEMBER) // 不是黑名单会员
					.creditPoint(0) // 初始会员积分都为0
					.telephoneNumber(telephoneNumber) // 手机号码
					.avatarUrl(userInfoDTO.getAvatarUrl()) // 头像地址
					.wechatOpenId(wechatOpenId) // 微信OpenId
					.build();
			memberRepository.save(memberEntity);
			log.info("用户注册成功，相关数据已经成功保存到数据库");
			return memberEntity;
		}
		return existMemberEntity;
	}

	/**
	 * 不管是出售抑或是购买一笔订单的用户，都会增加相应的会员积分
	 */
	@Override
	public void addMemberCreditPoint(MemberShipDTO memberShipDTO) {

		int creditPoint = 0;
		String wechatOpenIdBuyer = memberShipDTO.getWechatOpenIdBuyer();
		String wechatOpenIdSeller = memberShipDTO.getWechatOpenIdSeller();

		this.memberRepository.updateMemberShipLevel(wechatOpenIdBuyer, creditPoint,
				updateMemberShipLevelEnum(creditPoint).getName());
		this.memberRepository.updateMemberShipLevel(wechatOpenIdSeller, creditPoint,
				updateMemberShipLevelEnum(creditPoint).getName());
	}

	/**
	 * 根据会员的信用分更新会员的会员等级
	 *
	 * @param creditPoint
	 * @return
	 */
	private MemberShipLevelEnum updateMemberShipLevelEnum(int creditPoint) {
		if (creditPoint >= MemberShipConstant.NORMAL_UNDER && creditPoint <= MemberShipConstant.NORMAL_UP) {
			return MemberShipLevelEnum.NORMAL;
		} else if (creditPoint >= MemberShipConstant.SLIVER_UNDER && creditPoint <= MemberShipConstant.SLIVER_UP) {
			return MemberShipLevelEnum.SILVER;
		} else if (creditPoint >= MemberShipConstant.GOLDEN_UNDER && creditPoint <= MemberShipConstant.GOLDEN_UP) {
			return MemberShipLevelEnum.GOLDEN;
		} else {
			return MemberShipLevelEnum.DIAMOND;
		}
	}
}
