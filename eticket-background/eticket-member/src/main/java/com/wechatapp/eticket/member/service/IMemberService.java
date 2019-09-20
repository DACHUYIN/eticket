package com.wechatapp.eticket.member.service;

import com.wechatapp.eticket.core.dto.MemberShipDTO;
import com.wechatapp.eticket.member.dto.LoginResponseDTO;
import com.wechatapp.eticket.member.dto.UserInfoDTO;

public interface IMemberService {

	LoginResponseDTO login(UserInfoDTO userInfoDTO);

    LoginResponseDTO updateLoginUserInfo(String wechatOpenId);

	void addMemberCreditPoint(MemberShipDTO memberShipDTO);
}
