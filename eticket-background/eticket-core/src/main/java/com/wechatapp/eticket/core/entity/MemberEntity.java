package com.wechatapp.eticket.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.wechatapp.eticket.core.enums.GenderEnum;
import com.wechatapp.eticket.core.enums.MemberShipLevelEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@Table(name = "eticket_member")
@EqualsAndHashCode(callSuper = false)
@IdClass(MemberEntityPK.class)
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity extends BaseEntity {

	// 分库主键
	@Id
	@Column(name = "sharding_id")
	private String shardingId;

	// 用戶编号
	@Id
	@Column(name = "user_id")
	private Long userId;

	// 微信昵称
	@Column(name = "nickname")
	private String nickName;

	// 性别
	@Column(name = "gender")
	@Enumerated(EnumType.ORDINAL)
	private GenderEnum gender;

	// 手机号码
	@Column(name = "telephone_number")
	private String telephoneNumber;

	// 信用评分，每成功一单（成功指卖出或者买入都算），信用评分加1分
	// 分数达到一定的比例，会员等级就会提高
	@Column(name = "credit_point")
	private Integer creditPoint;
	
	// 会员等级
	@Column(name = "membership_level")
	@Enumerated(EnumType.STRING)
	private MemberShipLevelEnum memberShipLevel;

	// 用户头像地址
	@Column(name = "avatar_url")
	private String avatarUrl;

	// 黑名单标识
	@Column(name = "blacklist_flag")
	private String blacklistFlag;
	
	// 微信标识
	@Column(name = "wechat_openid")
	private String wechatOpenId;
}
