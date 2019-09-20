package com.wechatapp.eticket.member.dto;

import com.wechatapp.eticket.core.enums.GenderEnum;

import com.wechatapp.eticket.core.enums.MemberShipLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserInfoDTO {

	/**
	 * 临时登录凭证
	 */
	private String code;

	/**
	 * 用户的微信标识
	 */
	private String wechatOpenId;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户头像图片的 URL。URL 最后一个数值代表正方形头像大小（有 0、46、64、96、132 数值可选，0 代表 640x640 的正方形头像，
	 * 46 表示 46x46 的正方形头像，剩余数值以此类推。默认132），用户没有头像时该项为空。若用户更换头像，原有头像 URL 将失效。
	 */
	private String avatarUrl;

	/**
	 * 用户性别
	 */
	private GenderEnum gender;

	/**
	 * 信用评分
	 */
	private Integer creditPoint;

	/**
	 * 会员等级
	 */
	private MemberShipLevelEnum memberShipLevel;
	
	/**
	 * 手机号码
	 */
	private String telephoneNumber;
}
