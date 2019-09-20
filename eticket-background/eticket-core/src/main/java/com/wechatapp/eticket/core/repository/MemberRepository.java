package com.wechatapp.eticket.core.repository;

import com.wechatapp.eticket.core.enums.GenderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wechatapp.eticket.core.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

	@Query(" select m from MemberEntity m where m.wechatOpenId = ?1 ")
	MemberEntity getMemberByWechatOpenId(String wechatOpenId);

	@Transactional
	@Modifying
	@Query(" update MemberEntity m set m.nickName = ?1, m.gender = ?2, m.avatarUrl = ?3 where m.wechatOpenId = ?4 ")
	void updateMemberEntity(String nickName, GenderEnum gender, String avatarUrl, String wechatOpenId);

	@Transactional
	@Modifying
	@Query(" update MemberEntity m set m.creditPoint = ?2, m.memberShipLevel = ?3 where m.wechatOpenId = ?1 ")
	void updateMemberShipLevel(String wechatOpenId, int creditPoint, String memberShipLevel);
}
